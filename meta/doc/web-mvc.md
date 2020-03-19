# spring-webmvc 5.2.3.RELEASE

----------------------------------------
* **Dependency**
  ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
  ```
----------------------------------------
* **설정 목록**
  * MVC 설정 및 Spring core 설정을 포함합니다.    
  
  
  | 목록               | 내용                                                | 설명 |
  | ------------------ | --------------------------------------------------- | ---- |
  | Filter             | 공통 Filter 및 URL 패스 별 필터 처리 설정           | lucy-xss-filter 적용     |
  | Interceptor        | 핸들러 Interceptor 설정                          | 공통 Interceptor (아직없음)     |
  | CORS               | CORS 설정                                       | CORS 전역 설정     |
  | Multipart Resolver | Mulipart Resolver 설정                          | StandardMultipart 사용      |
  | Exception          | Exception                                      | @ControllerAdvice 사용 - 공통 Exception Handler |
  | Validation         | 핸들러 메서드 아규먼트 유효성 검증                   | @Valid 사용     |   
  
          
----------------------------------------   
* **MVC 설정 클래스(com.etoos.common.config.WebConfig)**
  * 아래 목록의 MVC 설정을 위해 WebMvcConfigurer를 상속받은 설정 클래스를 사용합니다.
    * **Interceptor**
      * 특정 핸들러 또는 공통적으로 적용해야 하는 경우 InterceptorRegistry에 추가합니다.
    * **CORS**
      * CORS를 Url 패스별 또는 전역적으로 적용하기 위해 CorsRegistry에 추가합니다.
      * 특정 핸들러에만 적용하고 싶은 경우 @CrossOrigin 애너테이션을 고려해 봐야합니다
    * **ResourceHandler**
      * ResourceHandler를 사용하여 정적 리소스 요청을 WAS Container의 DefaultServlet으로 위임하여 요청을 처리합니다.
      * 해당 프로젝트에서는 정적 리소스가 없으며 필요한 경우 아래 설정 방법을 이용합니다. 
    * **Filter**
      * web.xml 파일을 사용하지 않으므로 Servlet Filter 등록과 관련된 설정을 FilterRegistrationBean 사용합니다.
      * FilterRegistrationBean은 WebMVcConfigurer 클래스와 무관하므로 @Bean으로 등록하여 사용합니다.
      * XSS 공격을 막기 위한 lucy-xss-servlet-filter를 사용하기 위해 설정이 추가되었습니다.
    * **MessageConverter**
      * Spring Boot의 기본 제공 이외의 추가적인 MessageConverter를 아래 설정에서 추가합니다.
      * 요청 바디에 실려오는 XSS 공격을 막기 위해서 HttpMessageConverter에 MappingJackson2HttpMessageConverter을 커스텀하여 등록하였습니다.
    
               
  * MVC 설정 클래스      
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SampleInterceptor())
                .addPathPatterns("/**")
                .order(1);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:8080")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName(XssEscapeServletFilter.class.getName());
        registrationBean.setOrder(10);
        registrationBean.setFilter(new XssEscapeServletFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(htmlEscapingConverter());
    }

    private HttpMessageConverter htmlEscapingConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
        return new MappingJackson2HttpMessageConverter();
    }

}
```
----------------------------------------
* **Exception과 Validation**  
    * 본 프로젝트의 Exception은 아래의 그림과 같이 처리 흐름도를 갖습니다.
   ![exception-proecss](/meta/img/exception-process.jpg)  

* **Exception 공통 처리 핸들러** 
  * CommonExceptionHandler (com.etoos.common.handler.CommonExceptionHandler)
  * 요청 처리 과정에서 일어나는 모든 Exception을 아래의 핸들러에서 처리 합니다.
  * Exception에 관련된 응답 내용은 ResponseErrorVo 객체를 사용하며 Json 문자열로 변환되어 응답 합니다.
  * Validation과 관련된 Exception도 공통 Exception 핸들러에서 처리합니다.
  * Validation과 관련된 예제는 아래의 샘플을 참고하세요.
        
```java
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Object> handleCommonException(CommonException commonException, WebRequest webRequest) {
        return handleExceptionInternal(commonException, commonException.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        return handleExceptionInternal(ex, ValidationResult.create(bindingResult), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        return handleExceptionInternal(ex, ValidationResult.create(bindingResult), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        log.error("{} \r\n {}", ex.getStackTrace(), request);
        if (Objects.isNull(body))
            body = ex.getMessage();

        ResponseErrorVo responseErrorVo = new ResponseErrorVo(status.toString(), LocalDateTime.now(), body);
        return super.handleExceptionInternal(ex, responseErrorVo, headers, status, request);
    }

}
```
    
```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseErrorVo {

    String code;

    LocalDateTime timeStamp;

    Object message;
}
```
      
* **Validation 적용 Sample Class**    

```java
@Data
@EqualsAndHashCode(callSuper=false)
@Alias("sampleVo")
public class SampleVo extends CommonVo {

    private long id;
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    // 정규식으로 이메일의 유효성을 판단한다.
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    // @Email annotation 으로 이메일의 유효성을 판단한다.
    @Email()
    private String email1;

    // @Min, @Max annotation 으로 최소, 최대값을 지정한다.
    @Min(value = 18)
    @Max(value = 30)
    private int age;

    // 신용카드번호의 유효성을 판단한다.
    @CreditCardNumber
    private String creditCardNumber;

    private String modDt;
    private String modUser;
    private String phoneNumber;
    private String regDt;
    private String regUser;
    private String userAccount;

}
```    
    
* **Validation 사용 Sample Method**    
```java
    @PostMapping("")
    public ResponseEntity<DemoVo> createEntity(@Valid DemoVo demoVo) {
        demoVo = demoService.createEntity(demoVo);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(demoVo, httpHeaders, HttpStatus.OK);
    }
```

      
* **참고 사항**
  * @EnableWebMvc 사용 금지 -  Spring boot 자동 설정을 사용하지 못함
  * WebMvcConfigurer 을 사용하여 MVC 커스텀 설정 권장합니다.

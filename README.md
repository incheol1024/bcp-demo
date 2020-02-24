# Spring Boot 2.2.4 (2020.02.12 기준 GA)

​	**하위 스프링 관련 버전은 Spring Boot 2.2.4 버전의 부모 pom dependency를 기준으로 합니다.**



## 1. spring-webmvc 5.2.3.RELEASE

* **Dependency**

  ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
  ```

    

* **설정 목록**
  * MVC 설정 및 Spring core 설정을 포함합니다.    
  
  
  | 목록               | 내용                                                | 설명 |
  | ------------------ | --------------------------------------------------- | ---- |
  | DispatcherServlet  | 요청 URL 설정 (/**)                                 |      |
  | Filter             | 공통 Filter 및 URL 패스 별 필터 처리 설정           |      |
  | Interceptor        | 핸들러 Interceptor 설정                             |      |
  | CORS               | CORS 설정                                           |      |
  | Multipart Resolver | Mulipart Resolver 설정 (Default Standard Multipart) |      |
  | Exception          | Exception 공통 처리 및 각 핸들러 별 처리            |      |
  | Validation         | 핸들러 메서드 아규먼트 유효성 검증                  |      |
  | HATEOAS            | 헤이토스가 무엇인지??..                           |      |
  
  * Exception 설정  
   ![exception-proecss](/meta/img/exception-process.jpg)  
  
```java
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest webRequest) {
        log.error("{} \r\n {}", exception, webRequest);
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(exception.getMessage());
        errorMessage.setDebugMessage(exception.getLocalizedMessage());
        errorMessage.setTimestamp(LocalDateTime.now());
        errorMessage.setStatus(HttpStatus.NOT_FOUND);
        return this.handleExceptionInternal(exception, errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

}
```
    
```java
@Data
public class ErrorMessage {

    private HttpStatus status;

    private String message;

    private String debugMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;


}
```
  
  * Validation 설정
    * validation 대상 객체
```java
@Data
@Alias("demo")
public class DemoVo {

    @Min(value = 0, groups = Create.class)
    private long id;

    @NotNull(groups = Create.class)
    private String name;


    public DemoVo valueOf(DemoEntity demoEntity) {
        this.id = demoEntity.getId();
        this.name = demoEntity.getName();
        return this;
    }
    
}
```  
  
  * Validation group Interface
```java
public interface CrudInterface {

    interface Create { }

    interface Update { }

}
```
  
  * Validation 사용
```java
    @PostMapping("")
    public ResponseEntity<DemoVo> createEntity(@Validated(value = Create.class) DemoVo demoVo) {
        demoVo = demoService.createEntity(demoVo);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(demoVo, httpHeaders, HttpStatus.OK);
    }
```
      
* **참고 사항**
  * @EnableWebMvc 사용 금지 -  Spring boot 자동 설정을 사용하지 못함
  * WebMvcConfigurer 을 사용하여 MVC 커스텀 설정 권장


## 2. Spring Data JPA (hibernate 5.4.10.Final)

* **Dependency**

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  ```

    

 * **Demo Test 환경 구성**

   * h2 database 메모리DB (임시 구성)

   * Entity Class (예시)

     ```java
     @Entity
     public class DemoEntity {
     
         @Id
         @GeneratedValue(strategy = GenerationType.AUTO)
         long id;
     
         @Column
         String name;
     }
     ```

       

   * *JpaRepository 사용 (예시)

     ```java
     // Repository 기본 CrudRepository, PagingAndSortingRepository를 상속받은 JpaRepository
     public interface DemoRepository extends JpaRepository<DemoEntity, Long> {
     }
     ```

       

   * application.properties
    * 샘플 구성을 위해 h2 데이터베이스를 사용합니다.
```yaml
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        format_sql: true
        use_sql_comments: true
```

     

## 3. Spring Mybais
 * Spring boot 환경에서의 설정 학습이 필요  
 **Dev profile에서 콘솔 및 로그에 쿼리가 디테일하고 예쁘게 나오게 하기** 



## 4. Spring Redis Template
**Redis Cacheable로 사용해야 합니다.**
**아래 정리는 Redis Data를 넣고빼고 하는 세팅.**

* Dependency  

  ```
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>
  ```  
  
* Redis Client

  * Jedis vs Lettuce

  * [비교 자료](https://jojoldu.tistory.com/418)
  
  
* Redis Config 설정 

```java
  @Configuration
  @Profile("dev")
  @EnableRedisRepositories
  public class RedisConfig {
      @Bean
  public RedisConnectionFactory redisConnectionFactory() {
      LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory("127.0.0.1", 6379);
      return lettuceConnectionFactory;
  }
  
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
      RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
      redisTemplate.setConnectionFactory(redisConnectionFactory);
      redisTemplate.setKeySerializer(new StringRedisSerializer());
          return redisTemplate;
      } 
  }
```
  
  
* Redis disk write 설정 (Cacheable로 사용할거라 Disk write 없음)

  * RDB vs AOF 
  * [비교 자료](http://redisgate.kr/redis/configuration/persistence.php)
  * [장단점](https://ossian.tistory.com/42) 
  
  
* 참고 URL

  * [HashMap&Redis with Spring Data](https://brunch.co.kr/@springboot/73)

## 5. Spring Batch

spring batch tutorial 해보는 중입니다.  
https://spring.io/guides/gs/batch-processing/
  



## 6. Spring Security  
 **레거시 프로젝트트는 어떻게 활용하는지 볼 필요**
* Dependency  
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```

```java
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder.encode("111")).roles("user");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("admin")
                .antMatchers("/info").hasRole("user")
                .antMatchers("/", "/create/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated();

        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.formLogin();
        http.httpBasic();
*/

        http.authorizeRequests()
                .antMatchers("/**").permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


```


## 7. Springfox Swagger

* Dependency 

  ```xml
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.9.2</version>
  </dependency>
  
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.9.2</version>
  </dependency>
  ```
  
* Bugs
  * 해결 방법 
    * @SpringBootApplication 이 있는 메인 클래스에 @EnableSwagger2 설정
    * Docket 빈 설정도 메인클래스에 설정
    * version 2.9.2로 변경 (3.x.x 버전으로 사용해야합니다.)

  * <img src="C:\Users\etoos\AppData\Roaming\Typora\typora-user-images\image-20200213171957934.png" alt="image-20200213171957934" style="zoom:33%;" />
  
  
* Swagger UI 캡쳐

  <img src="C:\Users\etoos\AppData\Roaming\Typora\typora-user-images\image-20200213172754850.png" alt="image-20200213172754850" style="zoom:33%;" />


## 8. Spring Rest Docs
* 적용해보기


---
---
---

  

# Sample Package

* default  
![package-default](/meta/img/package-default.jpg)
  
* common package   
![package-common](/meta/img/package-common.jpg)
  
* config package  
![package-config](/meta/img/package-config.jpg)
  
* domain package  
![package-domain](/meta/img/package-domain.jpg)
  
  
  
---

# Sample Code

## MVC

 * ### Controller

 ```java
      @Slf4j
      @RestController
      @RequestMapping("/demo")
      public class DemoController {
      
          private DemoService demoService;
      
          @Autowired
          public DemoController(DemoService demoService) {
              this.demoService = demoService;
          }
      
          @PostMapping("")
          public ResponseEntity<DemoVo> createEntity(@Validated(value = Create.class) DemoVo demoVo) {
              demoVo = demoService.createEntity(demoVo);
              HttpHeaders httpHeaders = new HttpHeaders();
              httpHeaders.setContentType(MediaType.APPLICATION_JSON);
              return new ResponseEntity(demoVo, httpHeaders, HttpStatus.OK);
          }
      
          @GetMapping("/{id}")
          public DemoVo findEntity(@PathVariable long id) {
              return demoService.findEntity(id);
          }
      
          @PutMapping("/{id}")
          public ResponseEntity<DemoVo> updateEntity(@Validated(value = Update.class) DemoVo demoVo) {
              demoVo = demoService.updateEntity(demoVo);
              HttpHeaders httpHeaders = new HttpHeaders();
              httpHeaders.setContentType(MediaType.APPLICATION_JSON);
              return new ResponseEntity<>(demoVo, httpHeaders, HttpStatus.OK);
          }
      
          @DeleteMapping("/{id}")
          public ResponseEntity deleteEntity(@PathVariable long id) {
              HttpStatus httpStatus = demoService.deleteEntity(id);
              return ResponseEntity.status(httpStatus).build();
          }
}
```

      

 * ### Service

```java
@Service
@Transactional
public class DemoService {

    private DemoRepository demoRepository;


    @Autowired
    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    public String findNameById(long id) {
        DemoEntity demoEntity = demoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found id"));

        DemoVo demoVo = new DemoVo();
        demoVo.setId(demoEntity.getId());
        demoVo.setName(demoEntity.getName());

        return demoVo.getName();
    }

    public DemoVo findEntity(long id) {
        DemoEntity demoEntity = demoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Entity id:" + id));
        DemoVo demoVo = new DemoVo();
        return demoVo.valueOf(demoEntity);
    }

    public DemoVo createEntity(DemoVo demoVo) {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setValueOfDemoVo(demoVo);
        demoEntity = demoRepository.save(demoEntity);
        demoVo.valueOf(demoEntity);
        return demoVo;
    }

    public DemoVo updateEntity(DemoVo demoVo) {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setValueOfDemoVo(demoVo);
        demoEntity = demoRepository.save(demoEntity);
        demoVo.valueOf(demoEntity);
        return demoVo;
    }


    public HttpStatus deleteEntity(long id) {
        demoRepository.deleteById(id);
        return HttpStatus.OK;
    }
}
```

      

 * ### Entity

```java
      @Entity
      public class DemoEntity {
      
          @Id
          @GeneratedValue(strategy = GenerationType.AUTO)
          long id;
      
          @Column
          String name;
      
          public long getId() {
              return id;
          }
      
          public void setId(long id) {
              this.id = id;
          }
      
          public String getName() {
              return name;
          }
      
          public void setName(String name) {
              this.name = name;
          }
      
          @Override
          public String toString() {
              return "DemoUser{" +
                      "id=" + id +
                      ", name='" + name + '\'' +
                      '}';
          }
      }
```

 * ### Repository

```java
      public interface DemoRepository extends JpaRepository<DemoEntity, Long> {
      }
```

 * ### Vo

```java
@Data
@Alias("demo")
public class DemoVo {

    @Min(value = 0, groups = Create.class)
    private long id;

    @NotNull(groups = Create.class)
    private String name;


    public DemoVo valueOf(DemoEntity demoEntity) {
        this.id = demoEntity.getId();
        this.name = demoEntity.getName();
        return this;
    }


}
```
  
  
## MVC TEST
* ### 통합 테스트 (요청 -> 서비스 -> 응답)
```java
      @SpringBootTest
      @AutoConfigureMockMvc
      @ActiveProfiles("dev")
      class DemoControllerTest {
      
          @Autowired
          MockMvc mockMvc;
      
          @Autowired
          ObjectMapper objectMapper;
      
          private static String rootUrl;
      
          @BeforeAll
          static void beforeAll() {
              rootUrl = "/demo";
          }
      
          @Test
          void createEntity() throws Exception {
              String entityJson = "{\"name\":\"incheol\"}";
              this.mockMvc.perform(post(rootUrl).content(entityJson))
                      .andDo(print())
                      .andExpect(status().isOk());
          }
      
          @Test
          void findEntity() throws Exception {
              this.mockMvc.perform(get(rootUrl + "/1"))
                      .andDo(print())
                      .andExpect(status().isNotFound()); // 데이터 없어서 404
          }
      
          @Test
          void updateEntity() throws Exception {
              this.mockMvc.perform(put(rootUrl + "/1"))
                      .andDo(print())
                      .andExpect(status().isOk());
          }
      
          @Test
          void deleteEntity() throws Exception {
              this.mockMvc.perform(delete(rootUrl + "/1"))
                      .andDo(print())
                      .andExpect(status().is5xxServerError());
          }
      
          @Test
          void handleException() {
          }
      }
```

* ### 단위 테스트 (given, when, then)
```java
@ExtendWith(MockitoExtension.class)
public class DemoServiceSliceTest {

    @Mock
    private DemoRepository demoRepository;

    @Test
    void findEntity() {
        long id = 5;
        String name = "incheol";

        // given
        BDDMockito.given(demoRepository.findById(anyLong()))
                .willAnswer(invocation -> {
                    long argument = invocation.getArgument(0);
                    DemoEntity demoEntity = new DemoEntity();
                    demoEntity.setId(argument);
                    demoEntity.setName(name);
                    return Optional.of(demoEntity);
                });

        // when
        DemoService demoService = new DemoService(demoRepository);
        DemoVo entity = demoService.findEntity(id);

        // then & verify
        Assertions.assertThat(entity).matches(demoEntity ->
                        demoEntity.getId() == id && demoEntity.getName().equals(name)
                , "DemoEntity Fields")
                .satisfies(System.out::println);

        verify(demoRepository, new Times(1)).findById(anyLong());
    }
}
```
  
  
# 공통 코드
 ### Response format  
 * 참고 URL - https://github.com/cryptlex/rest-api-response-format
 
    
 ### DataSource 분리 설정
 * Configuration 파일
   
 ```java
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class })
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerPostgre"
        , transactionManagerRef = "transactionManagerPostgre"
        , basePackageClasses = DemoRepository.class)
public class DataSourcePostgreJpaConfiguration {


    @Primary
    @Bean("datasource-postgre-jpa")
    public DataSource dataSourcePostgre(DataSourceProperties datas) {
        DataSourcePropertyHolder postgreHolder = datas.getPostgreHolder();

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(postgreHolder.getDriverClassName());
        hikariDataSource.setJdbcUrl(postgreHolder.getUrl());
        hikariDataSource.setUsername(postgreHolder.getUserName());
        hikariDataSource.setPassword(postgreHolder.getPassword());
        return hikariDataSource;
//        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("entityManagerPostgre")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder
            , @Qualifier("datasource-postgre-jpa") DataSource dataSource) {

        return builder.dataSource(dataSource)
                .packages("com.etoos.bcpdemo.bcp")
                .persistenceUnit("postgre")
                .properties(additionalJpaProperties())
                .build();

    }




// 임시 샘플 코드
    Map<String, ?> additionalJpaProperties() {
        Map<String, String> map = new HashMap<>();

        map.put("hibernate.hbm2ddl.auto", "create");
        map.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        map.put("hibernate.show_sql", "true");

        return map;
    }


    @Primary
    @Bean("transactionManagerPostgre")
    public JpaTransactionManager jpaTransactionManager(@Qualifier("entityManagerPostgre") EntityManagerFactory managerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(managerFactory);
        return jpaTransactionManager;
    }


}
```
  
   * Configuration Properties 파일
   ```java
@ConfigurationProperties(prefix = "application.datasource")
@Data
public class DataSourceProperties {

    private DataSourcePropertyHolder postgreHolder = new DataSourcePropertyHolder();

    private DataSourcePropertyHolder msHolder = new DataSourcePropertyHolder();


    @Data
    public static class DataSourcePropertyHolder{

        private String driverClassName;

        private String url;

        private String userName;

        private String password;

    }


}
```
  
  * yaml 파일 설정하기
```yaml
# 애플리케이션이 사용하는 프레임워크 설정을 커스텀하기 위한 프로퍼티 입니다.
application:
  datasource:
    ms-holder:
      driver-class-name: org.h2.Driver
      url: mysql url
      user-name: mysql username
      password: mysql password
    postgre-holder:
      driver-class-name: org.h2.Driver
      url: jdbc:h2:mem:testdb
      user-name: sa
      password:
```

 ### 공통 Exception 처리
   * 공통 Exception 핸들러  
```java
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest webRequest) {
        log.error("{} \r\n {}", exception, webRequest);

        return this.handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }


    @ExceptionHandler(CommonException.class)
    public ResponseEntity handleCommonException(CommonException commonException, WebRequest webRequest) {
        log.error("{} \r\n {}", commonException.getStackTrace(), webRequest);
        DemoErrorMessage errorMessage = commonException.getErrorMessage();
        return handleExceptionInternal(commonException, errorMessage.getMessage(), new HttpHeaders(), errorMessage.getStatus(), webRequest);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException runtimeException, WebRequest webRequest) {
        log.error("{} \r\n {}", runtimeException, webRequest);
        return this.handleExceptionInternal(runtimeException, runtimeException.getMessage(), null, null, webRequest);
    }



    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return this.handleExceptionInternal(ex, ex.getAllErrors(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (Objects.isNull(body))
            body = ex.getMessage();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

```   













 
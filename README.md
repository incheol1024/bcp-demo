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
  
  * Exception 설정  
   ![exception-proecss](/meta/img/exception-process.jpg)  
  
  [Exception 코드 보러가기](#Exception-Sample)
  
  
  
  * Validation 설정
    * validation 대상 객체
    
```java
@Data
@Alias("demo")
@AllArgsConstructor
@NoArgsConstructor
public class DemoVo{

    @Min(value = 0, groups = Create.class)
    private long id;

    @NotNull(groups = Create.class)
    private String name;

    public static DemoVo createDemoVo(DemoEntity demoEntity) {
        DemoVo demoVo = new DemoVo(demoEntity.getId(), demoEntity.getName());
        return demoVo;
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
public class DemoController {
    public CommonModel createDemo(@Validated(value = Create.class) @RequestBody DemoVo demoVo) {
        log.info("{}", demoVo);
        return demoServiceJpa.createDemo(demoVo);
    }
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
// @Api 애너테이션은 뭐하는건지 모르겠음
@Api(value = "DemoController Api annotation"
        , protocols = "HTTP"
        , consumes = MediaType.APPLICATION_JSON_VALUE
        , produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {

    @Autowired
    DemoService demoService;

    @PostMapping(value = ""
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "DemoEntity를 생성합니다. id, name 을 메세지에 담아서 보내주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = CommonModel.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "값을 입력하지 말아주세요.")
            , @ApiImplicitParam(name = "name", value = "등록할 이름을 보내주세요. ex) hong gil dong")})
    @CachePut(value = "DemoVo", key = "#demoVo.id") // 최종 리턴 결과만 캐시에 저장함. 캐시에서 찾을 생각을 안함.
    @TimeChecker
    public CommonModel createEntity(@Validated(value = Create.class) @RequestBody DemoVo demoVo) {
        log.info("{}", demoVo);
        return demoService.createEntity(demoVo);
    }

    @GetMapping(value = "/{id}"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "DemoEntity를 조회합니다. id를 url 끝에 작성하여 보내주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE, response = CommonModel.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "조회할 아이디 값 ex) 1")})
    @Cacheable(value = "DemoVo", key = "#id") // 캐시에서 먼저 조회하고, 없으면 핸들러 메소드 실행후 결과값 캐시에 저장
    @TimeChecker
    public CommonModel findEntity(@PathVariable long id) {
        return demoService.findEntity(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "DemoEntity를 수정합니다. id와 변경할 값을 보내주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = CommonModel.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "변경하고 싶은 아이디 값 ex) 1")
            , @ApiImplicitParam(name = "name", value = "변경할 이름 ex) kim gil dong")})
    @CachePut(value = "DemoVo", key = "#demoVo.id")
    public CommonModel updateEntity(@Validated(value = Update.class) DemoVo demoVo) {
        return demoService.updateEntity(demoVo);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "DemoEntity를 삭제합니다. 삭제할 아이디를 url 패스 끝에 적어주세요."
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , response = CommonModel.class)
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id"
            , value = "삭제할 아이디 값 ex) 1"
            , required = true
            , dataType = "long"
            , paramType = "path")})
    @CacheEvict(value = "DemoVo", key = "#id") // 캐시 삭제
    public CommonModel deleteEntity(@PathVariable long id) {
        return demoService.deleteEntity(id);
    }

    @GetMapping("/test")
    public ResponseEntity<List<DemoEntity>> listResponseEntity() {
        DemoEntity entity = new DemoEntity();
        entity.setId(1);
        entity.setName("incheol");
        List<DemoEntity> demoEntities = new ArrayList<>();
        demoEntities.add(entity);
        demoEntities.add(entity);
        return new ResponseEntity<>(demoEntities, HttpStatus.OK);
    }

}

```
  
  
![swagger-1](/meta/img/swagger-1.jpg)

      

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
  
  
* ## MVC TEST
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
*
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
    
 * ### DataSource 분리 설정 (업데이트 필요)
 * Configuration 파일
   
 ```java
@Configuration
public class DataSourceConfiguration {


    @Bean("dataSourceForPostgresJpaMaster")
    @ConfigurationProperties(prefix = "datasource.postgres.jpa.master")
    public DataSource dataSourceForPostgresJpaMaster() {
        return new HikariDataSource();
    }

    @Bean("dataSourceForPostgresJpaSlaveOne")
    @ConfigurationProperties(prefix = "datasource.postgres.jpa.slave-one")
    public DataSource dataSourceForPostgresJpaSlaveOne() {
        return new HikariDataSource();
    }

    @Bean("dataSourceForPostgresMybatisMaster")
    @ConfigurationProperties(prefix = "datasource.postgres.mybatis.mater")
    public DataSource dataSourceForPostgresMybatisMaster() {
        return new HikariDataSource();
    }

    @Bean("dataSourceForPostgresMybatisSlaveOne")
    @ConfigurationProperties(prefix = "datasource.postgres.mybatis.slave-one")
    public DataSource dataSourceForPostgresMybatisSlaveOne() {
        return new HikariDataSource();
    }

    @Bean("routingDataSourceForPostgresJpa")
    public DataSource routingDataSourceForPostgresJpa(@Qualifier("dataSourceForPostgresJpaMaster") DataSource masterDataSource
            , @Qualifier("dataSourceForPostgresJpaSlaveOne") DataSource slaveDataSource) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", masterDataSource);
        dataSourceMap.put("read", slaveDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }

    @Primary
    @Bean
    public DataSource dataSourceForPostgresJpaMain(
            @Qualifier("routingDataSourceForPostgresJpa") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }


    @Slf4j
    static class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
        @Override
        protected Object determineCurrentLookupKey() {
            String dataSourceType =
                    TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "read" : "write";
            return dataSourceType;
        }
    }


}
```

  
  * yaml 파일 설정하기
```yaml
# 애플리케이션이 사용하는 프레임워크 설정을 커스텀하기 위한 프로퍼티 입니다.
datasource:
  postgres:
    jpa:
      master:
        jdbc-url: jdbc:postgresql://localhost:5432/testdb?createDatabaseIfNotExist=true
        username: incheol
        password: pass
        pool-name: postgres-jpa-master
      slave-one:
        jdbc-url: jdbc:postgresql://localhost:5432/testdb?createDatabaseIfNotExist=true
        username: incheol
        password: pass
        pool-name: postgres-jpa-slave-one


    mybatis:
      mater:
        jdbc-url: jdbc:postgresql://localhost:5432/testdb?createDatabaseIfNotExist=true
        username: incheol
        password: pass
        pool-name: postgres-mybatis-slave-master
      slave-one:
        jdbc-url: jdbc:postgresql://localhost:5432/testdb?createDatabaseIfNotExist=true
        username: incheol
        password: pass
        pool-name: postgres-mybatis-slave-slave-one
```

 * ### 공통 Exception 처리
   * 공통 Exception 핸들러
   * 참고 URL
        * Spring Exception Handling 참고 URL: https://www.baeldung.com/exception-handling-for-rest-with-spring  
        * ResponseStatusException 참고 URL: https://www.baeldung.com/spring-response-status-exception
   * 설명: 
        * RestControllerAdvice 애너테이션을 이용하여 여러 서비스 핸들링에서 발생할 수 있는 Exception 들을 한곳에서 처리하도록 구현하였습니다.
   ResponseEntityExceptionHandler 를 상속받아 Spring 에서 제공하는 기본적인 Exception 유형들을 사용할 수 있으며 
   우리는 CommonExceptionHandler 의 handleExceptionInternal 메소드에서 CommonModel(응답으로 내보내기 위한 모델 형태)을 최종적으로 셋팅 한 후 
   ResponseEntity 바디에 실어서 응답합니다.
   개발자는 예외를 던지고자 하는 경우 ResponseStatusException에 HttpStatus 및 Message(reason)를 생성하여 던지기만 하면 되며 응답 형태에 대해서 신경쓰지 않아도 됩니다.              
   
```java
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NotFoundException.class) // ResponseStatusException 사용한다면 NotFoundException 필요할까요?
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception, WebRequest webRequest) {
        return this.handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(CommonException.class) // ResponseStatusException 사용한다면 CommonException 필요할까요?
    public ResponseEntity<Object> handleCommonException(CommonException commonException, WebRequest webRequest) {
        return handleExceptionInternal(commonException, commonException.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException runtimeException, WebRequest webRequest) {
        if (runtimeException instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException)runtimeException;
            HttpStatus httpStatus = responseStatusException.getStatus();
            Object body = responseStatusException.getReason();
            return this.handleExceptionInternal(runtimeException, body, null, httpStatus, webRequest);
        }
        return this.handleExceptionInternal(runtimeException, runtimeException.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        return handleExceptionInternal(ex, ValidationResult.create(bindingResult), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{} \r\n {}", ex.getStackTrace(), request);
        CommonModel commonModel = new CommonModel();
        if (Objects.isNull(body))
            body = ex.getMessage();

        Info info = Info.create(String.valueOf(status.value()), status.name(), body);
        commonModel.setInfo(info);

/*
        if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            ValidationResult validationResult = ValidationResult.create(bindingResult);
            info = Info.create(String.valueOf(status.value()), status.name(), validationResult);
        } else {
            info = Info.create(String.valueOf(status.value()), status.name(), ex.getMessage());
        }
*/

        return super.handleExceptionInternal(ex, commonModel, headers, status, request);
    }
}   
```   

 * ### Response format  
    * 참고 URL - https://github.com/cryptlex/rest-api-response-format
    * 응답 형태
   - {  
        info: { },  
        datas: { }  
   }
 
    * 200
   
    ![response-ok](/meta/img/response-ok.jpg)  
   
    * 400 validation exception    
  
    ![response-400-bind](/meta/img/response-400-bind.jpg)  
   
    * 개발자 정의 (throw new ResponseStatusException 사용)  
   
    ![response-devworker](/meta/img/response-devworker.jpg)  
   
    * 예기치 못한 Exception (DB 중지하고 요청을 보내봤음)  
   
    ![response-RuntimeException](/meta/img/response-RuntimeException.jpg)  
   
   
  
 
 
#Exception Sample

 
 
 












 
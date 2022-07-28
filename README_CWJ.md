### 사용자 권한에 따라 서비스 이용에 제한을 두는 방법
```java
http.csrf().disable(); //csrf비활성화
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() ///user/**로 들어오면 인증이 필요
	   .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")//admin manager만 가능 
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //admin만 가능, 권한에 따라 서비스 차별화 가능 
	   .anyRequest().permitAll() //다른 요청은 다 허가
	   .and()                       //권한 없을 때 로그인 창이동
                .formLogin()
                .loginPage("/loginForm"); //로그인 페이지로 가라

```

### 스프링 시큐리티 필터가 스프링 필터 체인에 등록 됨
SecurityConfig extends **WebSecurityConfigurerAdapter** 클래스를 사용해서 
- WebSecurityConfigurerAdapter deprecated 되었기 때문에 추후 개선 방안 필요
- SecurityConfig가 생기면 spring security가 /login을 낚아채지 않는다.



```java
//빈 객체로 등록해서 암호화에 필요한 곳에 사용함
    @Bean 
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }
```
### 암호화 필요한 모듈에 사용하는 방법
```java

    @Autowired //자동 di 해서 사용 
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    String rawPassword = user.getPassword(); //원래 비밀번호 
    String encPassword = bCryptPasswordEncoder.encode(rawPassword); //암호화한 비밀번호
```
- db에는 암호화한 비밀번호를 집어 넣는다. 

## 07 27 
- 쿠렌토 maven dependency 다운 시 문제 발생
- 오픈비두 사용

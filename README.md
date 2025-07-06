## 👉🏼 바로인턴 백엔드 자바 프로젝트

### 🙋‍♂️ 프로젝트 설명
**바로인턴 백엔드 자바 프로젝트**는 **Java와 Spring Boot 기반 JWT 인증/인가 시스템**을 구현한 프로젝트입니다.

- 회원가입 / 로그인 API 구현
- JWT 기반 인증 및 인가 처리
- Swagger UI를 통한 테스트 제공

> **작성자**: 조예인

---

## 🛠 목차
1. [📌 API 명세](#-api-명세)
2. [🚀 실행 방법](#-실행-방법)
3. [🖥️ EC2 배포 주소](#-ec2-배포-주소)
4. [📚 기술 스택](#-기술-스택)
5. [📞 Contact](#-contact)

---

## 📌 API 명세

### ✅ 회원가입 / 로그인 / 관리자 권한 부여

| 기능           | Method | URL                                  | 요청 예시            | 응답 예시         | 상태코드   |
|----------------|--------|---------------------------------------|----------------------|--------------------|------------|
| 회원가입        | POST   | `/auth/signup`                        | JSON Body            | 회원 정보           | 200 OK     |
| 로그인          | POST   | `/auth/login`                         | JSON Body            | JWT Access Token   | 200 OK     |
| 관리자 권한 부여 | PATCH | `/auth/users/{userId}/roles`          | PathVariable          | 수정된 회원 정보     | 200 OK     |

---

## 🚀 실행 방법

1. **프로젝트 클론**
```bash
git clone https://github.com/yourname/jwt-auth.git
cd jwt-auth
```

2. 빌드 및 실행

```bash
./gradlew clean build
java -jar build/libs/jwtAuth-0.0.1-SNAPSHOT.jar
```

3. Swagger 접속
- 로컬: http://localhost:8080/swagger-ui/index.html
- 배포: http://3.35.225.38:8080/swagger-ui/index.html

## 🖥️ EC2 배포 주소
http://3.35.225.38:8080/swagger-ui/index.html

## 📚 STACKS
- Java 17
- Spring Boot 3.5.3
- Spring Security
- JWT (jjwt)
- Spring Data JPA + H2
- Swagger (springdoc-openapi v3)

## 📞 Contact
- [🚗 Visit codingTrip blog](https://codingtrip.tistory.com/)
- [🙋‍♂️ Visit codingTrip's Github](https://github.com/codingTrip-IT)

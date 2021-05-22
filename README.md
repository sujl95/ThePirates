# ThePirates 채용 과제

## 개발 환경

- Window 10
- gradle-6.8.3
- java 11
- Spring Boot 2.4.5
- h2 database
- Spring Data Jpa

## 기술 스택

- Spring Boot
- Spring Data JPA
- H2
- JUnit5
- Swagger

## 빌드 및 실행

```java
git clone https://github.com/sujl95/ThePirates.git

cd (clone경로) 
gradle build
java -jar -Dfile.encoding="UTF-8" build/libs/thepirates-0.0.1-SNAPSHOT.jar
```

### API 명세서(Swagger)

- [http://localhost:8080/swagger-ui-custom.html](http://localhost:8080/swagger-ui-custom.html)

## 사용법

### http

```java
경로 /api/Shop.http
```

### A 점포 추가 API

```java
POST http://localhost:8080/api/shops
```

```java
{
   "name": "인어수산",
   "owner": "장인어",
   "description": "인천소래포구 종합어시장 갑각류센터 인어수산",
   "level": 2,
   "address": "인천광역시 남동구 논현동 680-1 소래포구 종합어시장 1층 1호",
   "phone": "010-1111-2222",
   "businessTimes": [
      {
         "day": "Monday",
         "open": "13:00",
         "close": "23:00"
      },
      {
         "day": "Tuesday",
         "open": "13:00",
         "close": "23:00"
      },
      {
         "day": "Wednesday",
         "open": "09:00",
         "close": "18:00"
      },
      {
         "day": "Thursday",
         "open": "09:00",
         "close": "23:00"
      },
      {
         "day": "Friday",
         "open": "09:00",
         "close": "23:00"
      }
   ]
}
```

### B. 점포 휴무일 등록 API

```java
POST http://localhost:8080/api/shops/holiday
```

```java
{
	 "id": 1,
	 "holidays": [
		 "2021-05-22",
		 "2021-05-23"
	 ]
}
```

### C. 점포 목록 조회 API

```java
GET http://localhost:8080/api/shops
```

```java
[
  {
    "name": "해적수산",
    "description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
    "level": 1,
    "status": "CLOSE"
  },
  {
    "name": "인어수산",
    "description": "인천소래포구 종합어시장 갑각류센터 인어수산",
    "level": 2,
    "status": "HOLIDAY"
  }
]
```

### D. 점포 상세 정보 조회 API

```java
GET http://localhost:8080/api/shops/2
```

```java
{
  "id": 2,
  "name": "해적수산",
  "description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
  "level": 1,
  "address": "서울 동작구 노량진동 13-8 노량진수산시장 활어 001",
  "phone": "010-1234-1234",
  "businessDays": [
    {
      "day": "Monday",
      "open": "09:00",
      "close": "24:00",
      "status": "CLOSE"
    }
  ]
}
```

### E. 점포 삭제 API

```java
DELETE http://localhost:8080/api/shops/1
```
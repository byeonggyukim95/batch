Upbit 분(Minute) 캔들 조회 API를 활용한 알림/후처리 Step(매시간 1초)을 실행하는 Spring batch

---
## Database

- `batch_db` : Spring Batch 메타데이터 전용
- `coin_db`  : 코인 데이터 저장용

### 1. Database 생성

```sql
CREATE DATABASE `batch_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE `coin_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
### 2. Batch 메타 테이블

```md
IDE에서 파일 검색: schema-mysql.sql
```
### 3. Coin 테이블 생성

```sql
CREATE TABLE `coin_candle` (
  `market` varchar(15) NOT NULL,
  `candle_date_time_kst` varchar(25) NOT NULL,
  `opening_price` decimal(20,8) NOT NULL,
  `trade_price` decimal(20,8) NOT NULL,
  `unit` int NOT NULL,
  PRIMARY KEY (`market`)
) ENGINE=InnoDB;
```

---

- 데이터 출처: Upbit Open API
- https://docs.upbit.com/kr/reference/list-candles-minutes
- 본 프로젝트는 개인 학습/포트폴리오 목적이며, 데이터/문서의 무단 재배포 및 상업적 이용을 목적으로 하지 않습니다.

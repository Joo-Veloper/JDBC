# JDBC(Java Database Connectivity)
자바 애플리케이션이 다양한 관계형 데이터베이스와 통신할 수 있게 해주는 표준 API입니다. 
JDBC는 데이터베이스와 연결하고, SQL 문을 실행하고, 그 결과를 처리하는 기능을 제공합니다. 

### JDBC 표준 인터페이스

JDBC는 기본적으로 **세 가지 표준 인터페이스**를 중심으로 구성됩니다:

1. **java.sql.Connection - 연결 관리**
    - **설명**: Connection 인터페이스는 자바 애플리케이션과 데이터베이스 사이의 연결을 나타냅니다. 데이터베이스에 연결을 생성하고, 그 연결을 통해 트랜잭션을 시작하거나 종료하는 등의 작업을 수행합니다.
    - **주요 기능**:
        - 데이터베이스 연결 생성 및 종료
        - 트랜잭션 관리 (커밋, 롤백 등)
        - PreparedStatement와 같은 SQL 문을 준비하는 객체를 생성

2. **java.sql.Statement - SQL 명령 실행**
    - **설명**: Statement 인터페이스는 SQL 쿼리를 데이터베이스에 전달하는 역할을 합니다. SQL 문을 실행하고 그 결과를 반환받는 기본 수단입니다. 이를 통해 SELECT, INSERT, UPDATE, DELETE 같은 쿼리를 실행할 수 있습니다.
    - **주요 기능**:
        - SQL 쿼리 실행 (executeQuery, executeUpdate, execute 등)
        - 동적으로 SQL 문을 생성해서 실행

   JDBC는 이 외에도 PreparedStatement(사전 컴파일된 SQL 문을 실행하는 데 사용)와 CallableStatement(저장 프로시저를 호출하는 데 사용)라는 인터페이스도 제공합니다. 이들은 Statement의 하위 클래스입니다.

3. **java.sql.ResultSet - 결과 처리**
    - **설명**: ResultSet 인터페이스는 데이터베이스에서 SELECT 쿼리의 결과를 저장하고, 이를 자바 애플리케이션이 처리할 수 있도록 제공합니다. 일반적으로 데이터베이스의 행(row)과 열(column) 데이터를 순차적으로 읽어 들이는데 사용됩니다.
    - **주요 기능**:
        - 결과 집합을 순회(next() 메서드 사용)
        - 각 열의 데이터를 가져오기 (getInt(), getString() 등)
        - 행 단위로 결과 데이터를 처리

### JDBC 드라이버

JDBC는 인터페이스만 제공할 뿐, 실제로 데이터베이스와 통신하는 부분은 각 DBMS(Database Management System)에서 제공하는 JDBC 드라이버가 담당합니다. 
JDBC 드라이버는 자바 애플리케이션과 데이터베이스 사이의 중간 계층 역할을 하며, 각 데이터베이스에 맞는 방식으로 JDBC 인터페이스를 구현합니다.

#### JDBC 드라이버의 역할
- JDBC API의 표준 인터페이스를 구현하여, 데이터베이스 벤더마다 다른 통신 프로토콜을 동일하게 처리
- 예를 들어, MySQL 데이터베이스와 통신하려면 MySQL JDBC 드라이버를 사용하고, Oracle 데이터베이스와 통신하려면 Oracle JDBC 드라이버를 사용합니다.

### JDBC 동작 방식

1. **드라이버 로드**:
    - JDBC 드라이버를 클래스패스에 추가하고, DriverManager를 통해 드라이버를 로드합니다. 예를 들어, MySQL JDBC 드라이버를 사용하는 경우
      ```java
      Class.forName("com.mysql.cj.jdbc.Driver");
      ```

2. **데이터베이스 연결**:
    - DriverManager.getConnection() 메서드를 사용해 데이터베이스와 연결합니다.
      ```java
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbname", "user", "password");
      ```

3. **SQL 실행**:
    - 연결이 완료되면, Statement 객체를 생성하여 SQL 문을 실행합니다.
      ```java
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM users");
      ```

4. **결과 처리**:
    - ResultSet을 통해 SQL 쿼리 결과를 읽고 처리합니다.
      ```java
      while(rs.next()) {
          System.out.println(rs.getString("name"));
      }
      ```

5. **리소스 정리**:
    - 모든 작업이 끝나면, ResultSet, Statement, Connection을 닫아 리소스를 해제합니다.
      ```java
      rs.close();
      stmt.close();
      conn.close();
      ```

### JDBC 드라이버 종류

JDBC 드라이버는 네 가지 유형으로 나뉩니다

1. **Type 1: JDBC-ODBC 브리지 드라이버**
    - Java에서 JDBC 호출을 ODBC(Open Database Connectivity) 호출로 변환합니다.
    - 오래된 방식으로, 거의 사용되지 않습니다.

2. **Type 2: 네이티브 API 드라이버**
    - 데이터베이스의 네이티브 API를 호출하는 드라이버. 각 플랫폼에 맞는 네이티브 라이브러리가 필요합니다.

3. **Type 3: 네트워크 프로토콜 드라이버**
    - 미들웨어 서버를 통해 JDBC 요청을 전달하고, 데이터베이스와 통신하는 방식입니다. 여러 데이터베이스를 지원하는 미들웨어 서버가 필요합니다.

4. **Type 4: 순수 자바 드라이버**
    - 순수 자바 코드로 작성된 드라이버로, 가장 일반적으로 사용됩니다. 자바 애플리케이션과 DBMS 간에 직접 통신합니다. MySQL, Oracle 등이 제공하는 드라이버가 이 유형입니다.

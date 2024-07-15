출처 : Java의 정석_기초편



# 예외처리

## 프로그램 오류

Compile Error : 컴파일 시 발생

Runtime Error : 실행 시 발생

Logical Error : 실행은 되지만, 의도와 다르게 동작



- 모든 예외의 최고 조상은 Exception Class



## Exception과 RuntimeException

- Exception : 사용자의 실수와 같은 외적인 요인에 의해 발생하는 예외
  - ArrayIndexOutOfBoundsException, NullPointerException, ClassCastException, ArithmeticException

- RuntimeException : 프로그래머의 실수로 발생하는 예외
  - FileNotFoundException, ClassNotFoundException, DataFormatException



## try-catch

- try-catch 문으로 예외를 처리한다.

  ```java
  try{
    // 예외발생 가능성이 있는 문장
  }catch (Exception1 e1){
    // 예외처리
  }finally{
    // 예외발생 여부와 상관없이 항상 수행되어야 하는 문장
  }
  ```

  

- 고의적인 예외발생은 Exception e = new Exception("고의발생"); 과 같이 구현 가능

  - throw e;로 발생시킬 수 있음

- 예외를 메서드에 선언하는 throws

  - 메서드를 사용하려는 사람에게 처리해야되는 예외를 알려주는 역할



## 사용자 정의 예외 만들기

```java
class MyException extends Exception{
  MyException(String msg){
    super(msg);
  }
}
```



# java.lang 패키지와 유용한 클래스

## Object클래스

- protected Object **clone**() : 객체 복사본 반환
- public boolean **equals**(Object obj) : 객체가 같은지
- public Class **getClass**() : 객체 자신의 클래스 정보를 담고 있는 Class 인스턴스를 반환
- public int **hashCode**() : 객체 자신의 해시코드를 반환
- public String **toString**() : 객체 자신의 정보를 문자열로 반환
- public void **notify**() : 객체 자신을 사용하려고 기다리는 스레드를 하나만 깨운다
- public void **notifyAll**() : 객체 자신을 사용하려고 기다리는 모든 스레드를 깨운다
- public void **wait**() : 다른 쓰레드가 notify()나 notifyAll()을 호출할 때까지 현재 스레드를 무한히 또는 지정된 시간동안 기다리게 한다.



## String클래스

- String(String s) : 주어진 문자열을 갖는 String 인스턴스를 생성
- String(char[] value) : 주어진 문자열을 갖는 String인스턴스를 생성
- String(StringBuffer buf) : StringBuffer인스턴스가 갖고 있는 문자열과 같은 내용의 String인스턴스를 생성
- char charAt(int index) : 지정된 위치에 있는 문자를 알려준다.
- int compareTo(String str) : 문자열과 사전순서로 비교(같으면 0, 이전이면 음수, 이후면 양수)
- String concat(String str) : 문자열을 뒤에 붙인다.
- boolean contains(CharSequence s) : 지정된 문자열이 포함되었는지를 검사
- boolean endsWith(String suffix) : 지정된 문자열로 끝나는지 검사
- boolean equals(Object obj) : 매개변수로 받은 문자열과 String 인스턴스의 문자열을 비교
- boolean equalsIgnoreCase(String str) : 문자열과 String인스턴스의 문자열을 대소문자 구분 없이 비교
- int indexOf(int ch) : 주어진 문자가 문자열에 존재하는 위치(없으면 -1)



## StringBuffer클래스

String 클래스와 다르게 문자열 변경이 가능하다.

- 내부적으로 버퍼를 가지고 있으며, 생성할 때 크기 지정 가능
- 배열처럼 append로 덧붙이기 가능
- 비교 시 toString()으로 변환하여 equals로 비교해야 함
  - 이유 : StringBuffer가 equals메서드를 오버라이딩하지 않아서.
- capacity(), charAt(int index), delete(start, end), deleteCharAt(index), insert(), length(), replace(start, end, str), reverse(), setCharAt(), setLength(), toString(), substring()



## Math클래스

- abs(), ceil(), floor(), max(a,b), min(a,b), random(), rint(a), round(a)



## 문자열을 숫자로 변환

- 문자열 -> 기본형
  - byte b = Byte.parseByte("100");

- 문자열 -> 래퍼 클래스(기본형을 객체로 다룰 수 있다)

  - Byte b = Byte.valueOf("100");

  - 오토박싱 : 기본형 값을 래퍼 클래스의 객체로 자동변환
  - 언박싱 : 반대로 변환하는 것(래퍼 클래스의 객체를 기본형 값으로.)
### Kotlin

안드로이드 개발 을 kotlin으로 하는 이유
- JVM 위에서 동작
- 불필요한 코드없이 간결하게 작성가능
- 구글의 2017년 이후 전폭적지지

kotlin은 어떻게 java와 상호 운용이 가능 한가
- kotlin은 컴파일 시 바이트 코드를 생서하기 때문에 바이트 코드로 jvm에서 로드 하여 사용하기 때문에 가능하다
- 실행 순서 소스 파일 -> 바이트 코드 -> JVM 로드

== vs === 
- == Value 비교
- === reference 비교

Class 생성 시 순서
- Constructor argument 먼저(부모 클래스가 있다면 여기서 부모 클래스로 이동)
- 초기화 코드 순서 변수 할당 , Init block 동일 순서
- Constructor 내부 함수 실행

const와 val의 차이점
- Const 상수, val 불변 객체

Inline fun
- 익명함수 또는 람다식에 대한 객체 할방을 방지 함
- 동작 방식 inline 함수의 코드를 호출한 위치로 이동 시킴
- 전달 받은 함수를 다른 함수에 넘겨주고 싶다면 noinline 키워드도 사용이 가능 하다.

Lateinit vs lazy
- Lateinit var을 쓰며 나중에 초기화할 것으로 간주
- lazy는 사용 시 런 타임에 초기화 val 사용

String StringBuilder StringBuffer 차이
- String : 불변하는 값에서 사용하기 좋다, 내부적으로 final로 선언되어 변경 시 비용이 크다
- StringBuilder : 값이 변할때 가장빠르나 멀티스레드환경에서 위험함
- StringBuffer : String보다 빠르지만 StringBuilder보다 느릴 속도 멀티스레드 환경에서 안점함

Hashcode equals
- Equals 동등성 연산 메서드 
- Hascode 특정 클래스의 구분 
- Data class 에서 rqu
* Class에 equals를 정의했다면, 반드시 hashCode도 재정의해야 한다.
* 2개 객체의 equals가 동일하다면 반드시 hashCode도 동일한 값이 리턴되어야 한다.
* 이런 조건을 지키지 않을 경우 HashMap, HashSet 등의 컬렉션 사용 시 문제가 생길 수 있다.

Sealed class
- Sub class들의 집합으로 subclass의 종류를 제한 하여 컴파일 타임에 유효성 검사를 할 수 있다.

private : 속해있는 class 에서만 접근가능
internal : private + 같은 모듈 안에서 접근가능
protected : private + 상속받은 클래스에서도 접근가능
public : protected + 접근제한 없음

메서드 영역 :  클래스와 인터페이스의 메타 데이터르 저장한다.
힙 영역  : 동적으로 생성된 객체가 저장되는 공간
스택 영역 : 메서드가 호출될 시 할당되는 영역입니다.

플랫폼 타입 : 느낌표 하나로 표시 되고 코드에서 명시적으로 사용 불가하며 nullable 또는 non-null 유형중 선택된 값을 사용하게 된다

확장합수 :  디자인 패턴을 사용하지 않고 새로운 기능으로 클래스를 확장 할 수 있는 기능, static 으로 만들어짐, 제네릭을 이용하여 좀더 유연하게 사용할 수 있다.

Array list 차이
* array의 경우 메모리에 공간을 미리 할당 받아서 사용 index로 접근, 메모리 상에 연속적으로 저장
* list의 경우 리스트 크기를 동적으로 사용하며 접근시에 point로 접근, 메모리 상에 비연속적으로 저장

인터페이스, 추상 클래스
* 코틀린에서는 경계가 모호해져서 큰차이점으로는 abstract는 생성자를 가질 수 있고 interface는 생성자를 가질 수 없는 차이가 있다.
* interface는 다중상속이 가능하다.

어노테이션 
* 어노테이션은 컴파일, 런타임 둘다 사용가능
* 어노테이션은 부가적인 정보를 주기위한 메타 데이터.
* 컴파일 타임에 존재하게 할 경우 어노테이션 프로세스를 통해서 정보를 가져오고, 런타임시에는 리플랙션으로 정보를 가져온다.

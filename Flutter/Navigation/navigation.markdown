## Flutter Navigation
Flutter 공부 첫 번째(Dart 언어 공부는 Dart언어 공부니까) 화면 이동하는 방법 화면을 이동하는 방법은 크게 Navigator의 `push`와 `pushNamed`를 이용하는 방법으로 나눌 수 있다. 
Navigator란 화면이동을 담당하며 화면간의 스택 유지 등을 처리한다.
push의 경우 부르고자 하는 화면을 바로 불러 올 수 있으며 넘겨줘야하는 파라미터도 생성자를 통해 편하게 전달이 가능하다.
pushNamed는 route를 미리 정의하고 필요한 파라미터도 미리 정의 하여 사용하는 방법으로 필요에 따라 유연하게 사용이 가능 하다.

### Navigator.push
push의 경우 사용법은 간단하다. context와 `MaterialPageRoute`를 정의 하면된다.
```dart
Navigator.push( context, MaterialPageRoute(builder: (context) => const SecondRoute()));
```
SecondRoute로 이동하는 방법이다. 파람터가 필요하다면 생성자를 수정하여 데이터를 넘겨줌녀 편하게 가능하다.

### Navigator.pushNamed
pushNamed의 경우 사용하기전에 먼저 `MaterialApp`에 `route`를 정의 해줘야한다. `route`를 정의하고 시작할 화면을 `initialRoute`로 정의한다.
```dart
MaterialApp(
  title: 'Named Routes Demo',
  initialRoute: '/',
  routes: {
    '/': (context) => const FirstScreen(),
    '/second': (context) => const SecondScreen(),
  },
)
```
실제로 사용방벙은 `push`와 별로 다르지 않다.
```dart
Navigator.pushNamed(context, '/second');
```

## pushNamed사용시 데이터 전달 방법
데이터를 전달히기 위해서는 pushNamed를 사용할 때 arguments를 전달하면 된다
```dart
Navigator.pushNamed(
    context, 
    '/second',
    arguments: "Arguments"
    );
```
받을때에는 ModalRoute.of를 이용하면된다.
```dart
final args = ModalRoute.of(context)!.settings.arguments as String;
```

### 화면 뒤로가기
`Navigator.pop()`를 사용한다.

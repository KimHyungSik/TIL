업데이트 날짜 2022.06.06

### 안드로이드 Comopse 사용 시 화면 이동 방법

Android Developers : [Android Compose Navigation](https://developer.android.com/jetpack/compose/navigation)

### 라이브러리 추가
```
dependencies {
    def nav_version = "2.4.2"

    implementation "androidx.navigation:navigation-compose:$nav_version"
}
```
별도로 [rememberAnimatedNavController](https://google.github.io/accompanist/navigation-animation/) Animation을 활용한 Navigation도 있다.

### NavController를 생성
```kt
val navController = rememberNavController()
```
navController는 Compose에서 naviation할 때 사용하느 컴퍼넌트로 화면 전황, 스택 관리 등을 진행 한다. Compose앱 내부에서 화면을 이동 할 때는 navConterller를 참조 해야한다.

### NacHost 생성
```kt
NavHost(navController = navController, startDestination = startDestination) {
    composable(startDestination) { Compoable() }
    composable(endDestination) { Compoable() }
}
```
아까 생성한 navController를 주입하고 startDestination을 설정하여 생성 할 수 있다.
composable에 route 형태로 화면 이동을 관리한다.

## NavGraph 분활
NavHost의 그래프를 분활 하여 작업 할 수 있기 떄문에 분활하여 작업하는 방법으 추천한다
```kt
fun NavGraphBuilder.subPage(navController: NavController){
    navigation(startDestination = "SubPage", route = "page"){
        composable("SubPage") {
            SubPage()
        }
    }
}

NavHost(navController = navController, startDestination = startDestination) {
    composable(startDestination) { Compoable() }
    subPage(navController)
}
```

route를 관리하는 방법 중 추천하는 방법은 saeled class를 이용하는 방법이 잇다.
```kt
sealed class Screen(val route : String){
  object MainScreen : Screen("MaingScreen")
}
```

### 화면 이동 방법
@Composable
fun MainScreen(navController: NavController) {
    Button(onClick = { navController.navigate("MainScreen2") }) {
        Text(text = "Navigate")
    }
}

생성해 두었던 navController를 이용하여 `navigate` 함수를 사용해 원하는 route로 이동 할 수 있다.
화면 이동 후 백스택의 화면을 Pop하기 위해 `popUpTo(route)`를 추가 할 수 있다.

### 화면 이동 시 데이터 전달
route에 인자를 추가하여 데이터를 전달 할 수 있다.
```kt
NavHost(startDestination = "MainScreen/{userId}") {
    composable(
    "MainScreen/{userId}",
    arguments = listOf(navArgument("userId") { type = NavType.StringType }
    ) { backStackEntry ->
        val userId = backStackEntry.arguments?.getString("userId")
        MainScreen(userId)
    }
}
```
arguments에 데이터를 정의 할 때 `nullability = true`(암시적으로 기본값을 null로 설정함), 
`defaultValue`(설정을 추가 하면 인자를 추가 하지 않아도 기본값을 사용 할 수 있다)를 추가 설정 할 수 있다.

arguments에 Json을 사용하여 전달 할 수 있다.
```kt
@Parcelize
data class Data(
    val name: String,
    val description: String
) : Parcelable
```
전달 할 객체를 NavType으로 사용 할 수 있도록 작업해야 합니다.
```kt
@Parcelize
data class Data(
    val name: String,
    val description: String
) : Parcelable{
    companion object NavigationType : NavType<Data>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Product? {
            return bundle.getParcelable(key)
        }

        override fun parseValue(value: String): Data {
            return Gson().fromJson(value, Data::class.java)
        }

        override fun put(bundle: Bundle, key: String, value: Data) {
            bundle.putParcelable(key, value)
        }
    }
}
```
CustomType을 생성 하였으면 이전과 같은 방식으로 사용 가능 합니다.
```kt
NavHost(startDestination = "MainScreen/{userId}") {
    composable(
    "MainScreen/{data}",
    arguments = listOf(navArgument("data") { type = Data.NavigationType }
    ) { backStackEntry ->
        val data = backStackEntry.arguments?.getString("data")
        MainScreen(data)
    }
}
```

전달할 객체를 savedStateHandle에 심어서 이용 할 수 도 있습니다.
```kt
// from
navController.currentBackStackEntry?.savedStateHandle?.set(
    "textData",
    TextData("textData")
)
            
// to
navController.previousBackStackEntry?.savedStateHandle?.get<TextData>("textData")
```

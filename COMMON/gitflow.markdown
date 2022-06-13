## git flow
![image](https://user-images.githubusercontent.com/54847106/173363393-1054f03d-18e8-4e9d-97ba-b2c2463c2c34.png)

git flow 명령어를 사용하여 git flow 흐름데로 정의하는 방법

## 브랜치 설명
### Master
- 배포되지 않은 완성된 버전의 코드 브랜치
- 지난 배포들의 태그들을 관리하는 main 브랜치

### develop
- 실질적으로 작업 및 코드들이 추가 되는 브랜치
- feature 브랜치에서 작업한 코드 또는 작업된 코드들을 develop방향으로 pr을 생성 코드리뷰를 받는다.

### feature
- 새로운 기능 개발, 버그 픽스시 사용 되는 브랜치
- 보통은 개발자 한명이 기능개발 후 develop에 merge하기 위해 작업을 진행한다.

### release
- develop 브랜치를 기준으로 생성되며 앱을 release하기 위해 생성하는 브랜치 이다.
- QA테스트를 기준으로 하며 이과정에서 발생한 이슈는 develop, release기준으로 들어간다.
- 앱 릴리즈 이후 master브랜치에 추가되면 릴리즈 시점에 태깅 된다.

### hotfix
- 다음 릴리즈를 기다릴 수 없을 정도로 긴급한 패치시 사용되는 브랜치이다.
- master브랜치를 기준으로 생성되며 이후 master브랜치에 합쳐지고 태깅 된다.
- 그후 develop 브랜치에도 병합된다.

일련의 과정들을 편하게 하기위해 gif-flow 를 지원한다

## git flow 사용

먼저 git flow를 초기화 시켜준다.

<img width="487" alt="스크린샷 2022-06-13 오후 10 37 21" src="https://user-images.githubusercontent.com/54847106/173366121-3518c2e7-8c99-4c8a-9cf8-4f87f410588d.png">

그러면 gitflow에 사용할 브랜치명을 지정할 수 있다. 엔터키로 기본값 사용이 가능 하다(-d 옵션으로 바로 생성할 수 도 있다)

다음 기능 개발을 위해 feature 브랜치를 생성해야한다. 

![스크린샷 2022-06-13 오후 10 41 03](https://user-images.githubusercontent.com/54847106/173366791-0f93433d-2e50-4cbc-bdeb-7ef75308534b.png)

```git flow feature start <branch name>```
명령어를 통해 feature를 시작 할 수 있으며 checkout 까지 바로 해주는 걸 알 수있다.

이후 작업이 완료되면 feature를 종료 할 수 있다.

![스크린샷 2022-06-13 오후 10 48 01](https://user-images.githubusercontent.com/54847106/173368140-dea090c9-2508-473a-89b6-991cb5b39a25.png)

```git flow feature finish <branch name>```
명령어로 feature를 종료시키고 develop에 병합 할 수 있다.

git hub에 push 하기 위해서는 
```git flow feature publish <brtanch name>```
을 사용 할 수 있으며
```git flow feature pull <remote name> <branch name>```
을 통해 브랜치르 가져 올수 있다.

기능 개발이 완료되고 앱을 배포하기 위해 release 브랜치를 사용하게되는데 

![스크린샷 2022-06-13 오후 10 52 41](https://user-images.githubusercontent.com/54847106/173369078-7bab1f4a-54f4-4af5-afc8-a5ca88154f9e.png)

```git flow release <branch name>```
명령어를 통해 생성 가능하다.

이후 과정을 feature의 명령어와 매우 비슷 하다.
```
git flow release finish <branch name>
git flow release publish <branch name>
git flow release track <branch name>
```

Finish 사용시 가장 먼저 확인할 수 있는 화면은 master브랜치와 병합할 커밋 메시지 수정 이다.(:wq 로 패스 가능)

<img width="461" alt="스크린샷 2022-06-13 오후 11 09 37" src="https://user-images.githubusercontent.com/54847106/173372641-af4f2ce5-b31f-4c9b-bad1-bac092e52689.png">

다음 화면은 태그 네임을 설정 할 수 있다. (태그를 추가 하지 않으면 수행 되지 않는다.

<img width="287" alt="스크린샷 2022-06-13 오후 11 09 51" src="https://user-images.githubusercontent.com/54847106/173372684-c1e586dc-b608-4846-b522-64f796c42c10.png">

마지막으로 develop 브랜치에 병합하는 커밋 메시지 작업이다.(:wq 로 패스 가능)

<img width="448" alt="스크린샷 2022-06-13 오후 11 10 07" src="https://user-images.githubusercontent.com/54847106/173372730-6704902a-77ac-45e3-a84e-af3bde4d3937.png">

1. Release 브랜치와 master 브랜치가 병합된다.
2. release 브랜치를 기준으로 태그를 생성하며 version tag prefix를 명시하면 명시한 워딩이 태그앞에 붙게 된다.
3. release 브랜치를 develop 브랜치에 병합한다.
4. release 브랜치르 삭제한다.

<img width="454" alt="스크린샷 2022-06-13 오후 11 10 24" src="https://user-images.githubusercontent.com/54847106/173372790-22c2e569-6416-4d41-922e-4bbebb66d9b2.png">

hotfix의 경우 release 사용과 유사하다.
```
git flow hotfix start <branch name>
git flow hotfix finish <branch name>
```

Hotfix finish 작업을 수행 하면
1. Hotfix는 master브랜치에 병합된다.
2. Hotfix 태깅이 진행 되고
3. Develop 브랜치에 병합된다.
4. hotfix 브랜치가 삭제된다
5. Develop 으로 checkout된다.

추가적으로 release, hotfix 작업 이후 git push \—tags를 잊지말자

Git flow 까먹지말고 잘사용하자.

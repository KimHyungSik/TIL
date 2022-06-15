git을 사용하다 보면 branch를 commit단위로 되돌려야하는 상황이 발생한다. 이 때 사용을 고민하게 되는것이 revert와 Reset이다.

## 차이점
reset : 특정 commit까지 되돌아가며 그전의 commit들은 완전히 삭제 한다. HEAD가 뒤로 돌아간다.
revert : 특정 commit 만 골라서 제거 한다. HEAD는 제자리에 있는다.

## 사용 법 
두개다 사용하기 전에 commit ID를 알아야한다. 
```git log```를 이용하여 확인 할 수 있으며 ```--graph``` 옵션을 추가하여 git의 로그를 그래프로 볼 수도 있다.
commitID는 7자 까지만 작성하여 사용해도 된다.

### reset
```
git reset --soft [commit ID] 
git reset --mixed [commit ID]
git reset --hard [commit ID]
```
soft : commit 전 상태로 되돌림
mixed : add 하기 전 상태로 되돌림
hard : 변경 사항을 모두 되돌림
```
git reset HEAD~10 commit 10개
git reset HEAD^ 현재 commit
```
추가적으로 commitID 대신 HEAD를 사용 할 수있다(상단의 옵션 사용 가능)

### revert
```
git revert [commit ID]
git revert HEAD
```
revert는 commitID, HEAD로 사용 가능하다
또한 revert사용시 커밋 메시지가 남게되는데 있때 commit message를 수정 하고 싶다면 뒤에 -e 옵션을 추가 하면 

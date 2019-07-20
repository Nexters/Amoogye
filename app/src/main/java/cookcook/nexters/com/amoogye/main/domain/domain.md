# domain

> 기능들을 정의 하는 부분이다. interface를 사용한다.

사용될 기능을 정의한다.
이곳에 정의되지 않은 기능을 임의로 추가하지 말아야 한다.

```
interface MainRepository {
    fun showToast(): void
}
```
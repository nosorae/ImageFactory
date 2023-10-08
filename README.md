# ImageFactory
Generate Image</br>
with Stable Diffusion</br>
without your own GPU powers

## 소개
### 한 줄 소개
Stable Diffusion API를 사용하여 이미지 생성을 위한 인프라를 관리를 하지 않아도 이미시 생성을 할 수 있는 앱입니다.

### 프로젝트 목표
1. 사용자는 모바일 앱으로 WebUi 보다 압도적으로 쉽게 stable diffusion을 경험할 수 있다.
2. 사용자는 타 10만 다운로드 이상의 stable diffusion 앱보다 더 쉽게 더 좋은 결과물을 생성할 수 있다.

### 개인 목표
1. 이미지 생성에 대해 입문하여 아이디에이션의 폭을 넓힌다.
2. Stable Diffusion API를 활용한 app 제작에 입문한다.
3. 구독 결제 BM을 바닥부터 구현함으로써 BM폭을 넓힌다.
4. 빠르게 기능을 완성하고 기술적인 방향성을 설정하여 리팩토링하는 경험을 해본다.

## 개발
### 언어
  - Kotlin
### 주요 기술 및 라이브러리
  - UI: Jetpack Compose
  - 비동기 작업: Coroutine/Flow
  - 의존성 주입: Hilt
  - Http 통신: Retrofit2
  - 로컬 데이터 저장: Room, Datastore-Preferenece
  - 기타 AAC: ViewModel, ViewModelScope/LifeCycleScope

### 아키텍처
![layered-architecture (none-domain-layer)](https://github.com/nosorae/ImageFactory/assets/62280009/0ef501be-7e6c-4784-a283-419bf8654eee)
- developers 에서 권장하는 Layered Architecture 이고 Domain Layer는 생략한 상태.
  - Domain 레이어를 생략한 이유는 기획 당시 재사용할만한 복잡한 비즈니스 로직이 없을 것이라고 판단했기 때문. 따라서 생략하여 개발속도를 높이고 필요할 때 추가하는 방향으로 설정. 
- UI Layer는 MVVM 패턴을 사용

### API
[stablediffusionapi](https://stablediffusionapi.com/)https://stablediffusionapi.com/ 라는 곳에서 제공하는 API를 사용.
#### API 선정 근거 (장점)
1. 모델 수가 많고 심지어 원하는 모델을 업로드할 수 있음.
2. 입문 가격이 저렴한 편. (최소금액 월 9$).
3. API 수도 꽤 있어 아이디에이션 폭이 넓어짐. (API 개수 69개)

#### API 단점
1. 한국 IP에서 볼 수 없는 이미지가 (꽤 많이) 있음. (두 번이나 문의해도 진행중이시라고 함)
2. 인터페이스가 일반적이지 않은 경우가 있음. (Boolean 의미를 전달하는데 String으로 YES, NO 이런식으로 주고받음)
3. 서버에서 프로세스중이라는 응답이나 에러 응답이 잦음 (약 10%정도, 경험적 측면에서 장점이 되기도 함.)

## ⚠️ 개선중 ⚠️
- Domain Layer 도입 필요
  - 모델리스트 같은 경우, 페이징이 불가한 API 라서 데이터베이스에 저장해두고 3일마다 업데이트하는 요구사항을 추가했는데 이 로직을 캡슐화하고 싶음.
  - Text-To-Image 요청 히스토리 같은 경우도 캡슐하고 재사용할 로직이 존재.
  - 또한, API응답이 일반적이지 않은 경우(ex. Boolean 의미를 String으로 주는 것)을 Domain에서 한번 끊어주는 용도로도 괜찮다고 생각.
- 꼼꼼한 예외사항 필요. 서버 OOM 같은 문서에는 나와있지 않은 에러가 발생하기도 함. 최소한 유저가 지금 어떤 에러가 발생해서 언제쯤 다시 시도할지는 알아야한다고 판단됨.
- 구독 결제 추가하여 광고 보지않고 이용할 수 있는 기능 추가
- 전문적인 용어 쉽게 풀어서 설명해주기
등등...

[ImageFactory 개인 프로젝트 문서](https://www.notion.so/Project-Image-Factory-084c9896137a4fc685a1742572ec7098)에 정리하며 개발하고 있습니다.</br>
일정, 기획 관련 메모, 개발 관련 메모를 관리하는 용도입니다.

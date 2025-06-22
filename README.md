# 🔍 MNISTScan

> TensorFlow Lite 기반 손글씨 숫자 인식 Android 데모 프로젝트  
> Python으로 모델을 학습하고, Android 앱에서 `.tflite` 모델로 추론합니다.

---

## 📌 프로젝트 개요

**MNISTScan**은 [MNIST 데이터셋](http://yann.lecun.com/exdb/mnist/)을 기반으로 손글씨 숫자를 분류하는 **엔드투엔드 데모 프로젝트**입니다.  
Python에서 모델을 학습하고 `.tflite`로 변환한 후, Android 앱에 적용하여 로컬 디바이스에서 실시간 추론을 수행합니다.

---

## 🧱 전체 구성

```
MNISTScan/
├── python/                    # Python - 모델 학습 및 TFLite 변환
│   ├── mnistData.py
│   ├── mnist_model.h5         # Keras 모델
│   └── mnist.tflite           # TFLite 변환 결과
│
├── android/                   # Android Studio 프로젝트
│   └── MNISTScanApp/
│       └── app/
│           └── src/main/assets/mnist.tflite
│
├── README.md
└── LICENSE
```

---

## 1. Python: 모델 학습 및 TFLite 변환

```bash
cd python/
pip install tensorflow numpy matplotlib
python mnistData.py
```

- 출력: `mnist_model.h5`, `mnist.tflite` 생성됨

---

## 2. Android: 추론 앱 실행

1. `mnist.tflite` 파일을 Android 앱의 `assets/` 폴더에 복사  
   - 위치: `android/MNISTScanApp/app/src/main/assets/`
2. Android Studio로 `android/` 열기
3. 앱 빌드 및 실행

> 손글씨를 화면에 직접 그리면 숫자가 인식되어 예측 결과가 표시됩니다.

---

## ✨ 주요 기능

- MNIST 숫자 분류 모델 학습 (Python, TensorFlow 2.x)
- `.tflite` 변환 및 저장
- Android 앱에서 손글씨 입력 후 로컬 추론 수행
- 온디바이스 추론으로 오프라인에서도 동작 가능

---

## 🛠 기술 스택

- **Python 3.9**
- **TensorFlow 2.x**
- **TensorFlow Lite**
- **Android (Kotlin + TFLite Interpreter API)**

---

## 📜 라이선스

이 프로젝트는 MIT License 하에 배포됩니다.  
자세한 내용은 [LICENSE](./LICENSE) 파일을 참고하세요.

---

## 🙋‍♀️ 제작자

- GitHub: [@gay00ung](https://github.com/gay00ung)

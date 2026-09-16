# 🍽️ Reserva de Mesas

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white)
![Status](https://img.shields.io/badge/status-concluído-brightgreen)

App Android para gerenciar a ocupação de mesas de um restaurante: uma tela de login simples e um painel com 9 mesas que podem ser reservadas ou liberadas individualmente ou em bloco, com a cor de "reservada" configurável e o estado salvo entre sessões.

## Funcionalidades

- **Login** com validação de credenciais antes de liberar o acesso ao painel;
- Painel com **9 mesas**, cada uma com botão próprio de reserva — ao reservar, a mesa muda de cor e o botão é desabilitado;
- **Liberar mesa por número**: digite o número da mesa (1 a 9) e libere sem precisar procurar visualmente;
- **Reservar todas as mesas** de uma vez, com aviso caso todas já estejam ocupadas;
- **Configurações de cor**: escolha entre vermelho, verde ou laranja para identificar as mesas reservadas — a escolha é salva e reaplicada nas mesas já ocupadas;
- **Persistência** do estado das mesas e da cor escolhida via `SharedPreferences`, mesmo depois de fechar o app;
- **Logout** que limpa todos os dados salvos e volta para a tela de login.

## Tecnologias

Java, Android SDK (`minSdk` 24, `compileSdk`/`targetSdk` 36), AndroidX (AppCompat, Material, ConstraintLayout, Activity), Gradle.

## Pré-requisitos

- [Android Studio](https://developer.android.com/studio) (recomendado, já traz JDK e SDK compatíveis);
- JDK 11+;
- Android SDK com a API 36 instalada;
- Um emulador ou dispositivo físico rodando Android 7.0 (API 24) ou superior.

## Como rodar localmente

```bash
git clone https://github.com/Torassi/Reserva_de_mesas.git
```

**Pelo Android Studio (recomendado):**

1. Abra o Android Studio → *Open* → selecione a pasta `Reserva_de_mesas`;
2. Aguarde o Gradle sincronizar as dependências;
3. Rode em um emulador ou dispositivo conectado clicando em ▶ *Run*.

**Pela linha de comando** (com o Android SDK configurado e `ANDROID_HOME` definido):

```bash
cd Reserva_de_mesas
./gradlew assembleDebug
```

O APK gerado fica em `app/build/outputs/apk/debug/app-debug.apk`, pronto para instalar num emulador ou dispositivo (`adb install`).

## Como usar

1. Na tela de login, informe as credenciais de teste definidas em `LoginActivity.java` (é um projeto de estudo, então o login é fixo no código — dá uma olhada lá se quiser testar);
2. No painel principal, toque em **RESERVAR** em qualquer mesa livre;
3. Para liberar uma mesa específica, digite o número dela no campo inferior e toque em **Liberar Mesa**;
4. Use **Reservar Todas as Mesas** para ocupar tudo de uma vez;
5. No ícone de configurações, escolha a cor usada para mesas reservadas;
6. Use **Logout** para limpar os dados salvos e voltar ao login.

## Autor

Leonardo Torassi

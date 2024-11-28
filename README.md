# HackathonWindowKnowledge

## API Reference

### 1. **GET /question/ask/{assistant_id}/{conversation_id}**

**URL:** `http://localhost:8080/question/ask/{assistant_id}/{conversation_id}`

- **Описание:**
  Принимает `assistant_id` и `conversation_id`, возвращает ответ от ассистента.

- **Параметры:**
    - `assistant_id` — ID ассистента
    - `conversation_id` — ID пользователя

---

### 2. **POST /data/pdf**

**URL:** `http://localhost:8080/data/pdf`

- **Описание:**
  Принимает PDF файл и преобразует его в текст, разбивая на части (chunks) по 200 слов.

- **Параметры:**
    - `pdf` — PDF файл для обработки

---

### 3. **POST /data/text**

**URL:** `http://localhost:8080/data/text`

- **Описание:**
  Принимает текстовый файл и разбивает его на части (chunks) по 200 слов.

- **Параметры:**
    - `text` — Текстовый файл для обработки

---

### 4. **POST /data/docx-file**

**URL:** `http://localhost:8080/data/docx-file`

- **Описание:**
  Принимает DOCX файл и преобразует его в текст, деля на chunks по 200 слов.

- **Параметры:**
    - `docx` — DOCX файл для обработки

---

### 5. **POST /data/text-file**

**URL:** `http://localhost:8080/data/text-file`

- **Описание:**
  Принимает текстовый файл и преобразует его в текст, деля на chunks по 200 слов.

- **Параметры:**
    - `text` — Текстовый файл для обработки

---

## Start Front-end

1. Перейдите в папку `public`:

    ```bash
    cd public
    ```

2. Установите зависимости:

    ```bash
    npm install
    ```

3. Запустите приложение:

    ```bash
    npm run dev
    ```

---

## Start Back-end

1. Соберите проект:

    ```bash
    mvn clean package
    ```

2. Запустите сервер:

    ```bash
    java -jar target/WindowOfKnowledge-0.0.1-SNAPSHOT.jar
    ```

---

### Дополнительно

- Чтобы использовать OpenAI модель, подставьте свой **API_KEY** в файл [application.properties](src/main/resources/application.properties).
- Если вы используете модель, которая требует API ключ, но векторизация не будет работать без ключа, убедитесь, что ключ правильно настроен.

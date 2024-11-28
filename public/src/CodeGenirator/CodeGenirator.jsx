import React, { useState } from 'react';
import { useColors } from "../ColorContext/ColorContext.jsx";

const EmbedCodeGenerator = () => {
    const { colors } = useColors(); // Получаем текущие настройки цветов
    const [embedCode, setEmbedCode] = useState("");

    // Функция для генерации кода для вставки
    const generateEmbedCode = () => {
        const settings = JSON.stringify(colors);
        const embedHTML = `
            <div class="preview-chat">
                <div id="chat-container" class="chat-container">
                    <div id="chat-messages" class="chat-messages"></div>
                    <div class="chat-input-area">
                        <input type="text" id="chat-input" class="chat-input" placeholder="Напишите сообщение...">
                        <button id="chat-send-button" class="chat-send-button">Отправить</button>
                    </div>
                </div>
                <div class="chat-open-button">
                    <button id="chat-toggle-button">Скрыть чат</button>
                </div>
                <script src="https://daniiltikhomirov.github.io/js-chat-support/chat.js"></script>
                <script src="https://daniiltikhomirov.github.io/js-chat-support/colorManager.js"></script>
                <script>window.addEventListener("load", () => applyCSSVariablesFromJSON(${localStorage.getItem('chatColors')}))</script>
            </div>`;
        setEmbedCode(embedHTML);
    };

    const libraryHTML = `<link rel="stylesheet" href="https://daniiltikhomirov.github.io/js-chat-support/chat.css">`;

    // Функция для копирования кода embedCode в буфер обмена
    const copyEmbedCodeToClipboard = () => {
        navigator.clipboard.writeText(embedCode)
            .then(() => {
                alert("Код для вставки скопирован в буфер обмена!");
            })
            .catch((err) => {
                console.error("Ошибка при копировании кода вставки: ", err);
            });
    };

    // Функция для копирования кода libraryHTML в буфер обмена
    const copyLibraryHTMLToClipboard = () => {
        navigator.clipboard.writeText(libraryHTML)
            .then(() => {
                alert("Необходимые импорты скопированы в буфер обмена!");
            })
            .catch((err) => {
                console.error("Ошибка при копировании импортов: ", err);
            });
    };

    return (
        <div className="embed-code-container">
            <button onClick={generateEmbedCode}>Генерировать код для вставки</button>
            {embedCode && (
                <>
                    <div>
                        <textarea
                            readOnly
                            rows="8"
                            cols="50"
                            value={embedCode}
                        />
                        <button onClick={copyEmbedCodeToClipboard}>Копировать код для вставки</button>
                    </div>

                    <div>
                        <textarea
                            readOnly
                            rows="8"
                            cols="50"
                            value={libraryHTML}
                        />
                        <button onClick={copyLibraryHTMLToClipboard}>Копировать импорты</button>
                    </div>
                </>
            )}
        </div>
    );
};

export default EmbedCodeGenerator;

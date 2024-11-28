import React, {useState} from "react";
import './Chat.css';

const Chat = () => {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");


    /**
     * Добавляет новое сообщение в историю сообщений.
     *
     * @param {string} content - Текст сообщения, которое нужно добавить.
     * @param {string} role - Роль отправителя сообщения. Может быть "user" или "assistant". По умолчанию - "user".
     */
    const addMessage = (content, role = "user") => {
        const newMessage = {
            role,
            content,
        };

        setMessages((prevMessages) => [...prevMessages, newMessage]);

        console.log(messages);
    };

    /**
     * Обрабатывает отправку сообщения при нажатии кнопки или клавиши Enter.
     * Добавляет сообщение от пользователя и имитирует ответ ассистента через 1 секунду.
     */
    const handleSend = () => {
        if (input.trim()) {
            addMessage(input, "user");
            setInput("");
            addMessage("Спасибо за ваше сообщение!", "assistant");
        }
    };

    /**
     * Обрабатывает нажатие клавиш на клавиатуре.
     * Если была нажата клавиша Enter, то выполняется отправка сообщения.
     *
     * @param {KeyboardEvent} e - Событие клавиатурного ввода.
     */
    const handleKeyDown = (e) => {
        if (e.key === "Enter") {
            handleSend();
        }
    };

    return (
        <div className="chat-container">
            <div className="chat-messages">
                {/* Отображаем все сообщения */}
                {messages.map((msg, index) => (
                    <div key={index} className={`chat-message ${msg.role}`}>
                        <div className="chat-bubble">
                            {msg.content}
                        </div>
                    </div>
                ))}
            </div>
            <div className="chat-input-area">
                <input
                    type="text"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    onKeyDown={handleKeyDown}  // Обрабатываем нажатие клавиш
                    className="chat-input"
                />
                <button onClick={handleSend} className="chat-send-button">
                    Отправить
                </button>
            </div>
        </div>
    );
};

export default Chat;

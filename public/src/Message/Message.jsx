const message = {
    role: "user",  // Или "assistant" — кто отправил сообщение
    content: "Ваш текст сообщения", // Текст сообщения
    timestamp: new Date().toISOString(), // Временная метка
};
export default message;
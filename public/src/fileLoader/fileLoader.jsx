import React, { useState } from 'react';
import './fileLoader.css';
import axios from "axios";

const FileLoader = () => {
    const [pdfFile, setPdfFile] = useState(null);
    const [docxData, setDocxData] = useState(null);
    const [txtData, setTxtData] = useState(""); // State to store TXT file content

    const handleFileUpload = async (e) => {
        const file = e.target.files[0];
        const fileType = file.name.split('.').pop().toLowerCase();

        if (fileType === 'pdf') {
            setPdfFile(file);

            // Формируем данные для отправки
            const formData = new FormData();
            formData.append("file", file);

            try {
                const response = await axios.post(
                    'http://localhost:8080/data/pdf?startPage=0&endPage=0',
                    formData,
                    {
                        headers: {
                            "Content-Type": "multipart/form-data",
                        },
                    }
                );
                console.log("Ответ сервера:", response.data);
            } catch (error) {
                console.error("Ошибка загрузки PDF файла:", error);
            }
        } else if (fileType === 'docx') {
            setDocxData(file);

            // Формируем данные для отправки
            const formData = new FormData();
            formData.append("avatar", file);

            try {
                const response = await axios.post(
                    'http://localhost:8080/data/docx-file',
                    formData,
                    {
                        headers: {
                            "Content-Type": "multipart/form-data",
                        },
                    }
                );
                console.log("Ответ сервера для DOCX:", response.data);
            } catch (error) {
                console.error("Ошибка загрузки DOCX файла:", error);
            }
        } else if (fileType === 'txt') {
            const reader = new FileReader();
            reader.onload = (event) => {
                setTxtData(event.target.result); // Store the content of the TXT file
            };
            reader.readAsText(file);
        } else {
            alert("Выберите файл формата PDF, DOCX или TXT.");
        }
    };

    const handleTxtChange = (e) => {
        setTxtData(e.target.value); // Update txtData as the user types
    };

    const handleSubmit = async () => {
        console.log("Submitted text:", txtData);
        try {
            await axios.post('http://localhost:8080/data/text', {
                "textData": txtData
            });
            alert("Текст успешно отправлен!");
        } catch (error) {
            console.error("Ошибка отправки текста:", error);
        }
    };

    return (
        <div className="loader-container">
            {/* Загрузка файлов */}
            <div className="settings-item">
                <label htmlFor="fileUpload">Загрузите файл (PDF/DOCX/TXT):</label>
                <input type="file" id="fileUpload" accept=".pdf,.docx,.txt" onChange={handleFileUpload} />
            </div>

            {/* Просмотр TXT */}
            {txtData && (
                <div className="txt-data">
                    <h3>Содержимое TXT файла:</h3>
                    <pre>{txtData}</pre>
                </div>
            )}

            {/* Написание текста */}
            <div className="txt-editor">
                <h3>Напишите свой текст:</h3>
                <textarea
                    value={txtData}
                    onChange={handleTxtChange}
                    rows="10"
                    cols="50"
                    placeholder="Введите текст..."
                />
            </div>

            {/* Кнопка отправки */}
            <div className="submit-button">
                <button onClick={handleSubmit}>Отправить</button>
            </div>
        </div>
    );
};

export default FileLoader;

import React, { useState } from 'react';
import * as XLSX from "xlsx";
import './fileLoader.css';

const FileLoader = () => {
    const [pdfFile, setPdfFile] = useState(null);
    const [excelData, setExcelData] = useState([]);
    const [txtData, setTxtData] = useState(""); // State to store TXT file content

    const handleFileUpload = (e) => {
        const file = e.target.files[0];
        const fileType = file.name.split('.').pop();

        if (fileType === 'pdf') {
            setPdfFile(file); // We just store the PDF file, but no preview
        } else if (fileType === 'xls' || fileType === 'xlsx') {
            const reader = new FileReader();
            reader.onload = (event) => {
                const data = new Uint8Array(event.target.result);
                const workbook = XLSX.read(data, { type: "array" });
                const sheetName = workbook.SheetNames[0];
                const sheet = workbook.Sheets[sheetName];
                setExcelData(XLSX.utils.sheet_to_json(sheet));
            };
            reader.readAsArrayBuffer(file);
        } else if (fileType === 'txt') {
            const reader = new FileReader();
            reader.onload = (event) => {
                setTxtData(event.target.result); // Store the content of the TXT file
            };
            reader.readAsText(file);
        }

        console.log(file);
    };

    const handleTxtChange = (e) => {
        setTxtData(e.target.value); // Update txtData as the user types
    };

    const handleSubmit = () => {
        // Handle submission, for example, log the text data
        console.log("Submitted text:", txtData);

        // You can replace this with actual submission logic (e.g., send to server)
        alert("Text submitted successfully!");
    };

    return (
        <div className="loader-container">

            {/* Загрузка файлов */}
            <div className="settings-item">
                <label htmlFor="fileUpload">Загрузите файл для обучения ассистента (PDF/Excel/TXT):</label>
                <input type="file" id="fileUpload" accept=".pdf,.xls,.xlsx,.txt" onChange={handleFileUpload} />
            </div>

            {/* Просмотр Excel */}
            {excelData.length > 0 && (
                <div className="excel-data">
                    <h3>Данные из Excel:</h3>
                    <table>
                        <thead>
                        <tr>
                            {Object.keys(excelData[0]).map((key, index) => (
                                <th key={index}>{key}</th>
                            ))}
                        </tr>
                        </thead>
                        <tbody>
                        {excelData.map((row, rowIndex) => (
                            <tr key={rowIndex}>
                                {Object.values(row).map((value, colIndex) => (
                                    <td key={colIndex}>{value}</td>
                                ))}
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}

            {/* Просмотр TXT */}
            {txtData && (
                <div className="txt-data">
                    <h3>Содержимое TXT файла:</h3>
                    <pre>{txtData}</pre> {/* Display the TXT content */}
                </div>
            )}

            {/* Write custom TXT */}
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

            {/* Submit Button */}
            <div className="submit-button">
                <button onClick={handleSubmit}>Отправить</button>
            </div>

        </div>
    );
};

export default FileLoader;

import React, { useState } from 'react';
import './ConfPage.css';
import { useColors } from "../ColorContext/ColorContext.jsx";
import Chat from "../Chat/Chat.jsx";
import CodeGenirator from "../CodeGenirator/CodeGenirator.jsx";
import FileLoader from "../fileLoader/fileLoader.jsx";

const ColorSettings = () => {
    const { colors, setColors } = useColors(); // Получаем значения и функцию для обновления цветов
    const [isChatVisible, setIsChatVisible] = useState(false); // Состояние для управления видимостью чата
    const [buttonImageUrl, setButtonImageUrl] = useState('');
    const { refreshColors } = useColors();


    const handleUrl = (e) => {
        setButtonImageUrl(e);
        setColors((prevColors => {
            return{...prevColors,
                ['buttonUrl']: e}
        }))
    }

    const handleColorChange = (e) => {
        const { name, value } = e.target;
        setColors((prevColors) => ({
            ...prevColors,
            [name]: value,
        }));
    };

    const handleRangeChange = (e) => {
        const { name, value } = e.target;

        setColors((prevColors) => {
            let newValue;

            if (value.endsWith("vh") || value.endsWith("vw") || value.endsWith("px") || value.endsWith("%")) {
                newValue = value;
            } else if (name.includes("Height")){
                newValue = `${value}vh`;
            }else if(name.includes("Width")){
                newValue = `${value}vw`;
            }else if(name.includes("Radius")){
                newValue = `${value}%`;
            }else {
                newValue = `${value}px`;
            }
            return {
                ...prevColors,
                [name]: newValue,
            };
        });
    };

    const toggleChatVisibility = () => {
        setIsChatVisible(!isChatVisible); // Переключаем состояние видимости чата
    };

    return (
        <div className="settings-container">
            <h2>Настройки цветов</h2>

            {isChatVisible && (
                <div className="preview-chat">
                    <Chat/>
                </div>
            )}

            <div className="loader">
                <FileLoader/>
            </div>

            <div className="chat-open-button">
                <button
                    onClick={toggleChatVisibility}
                    style={{
                        backgroundImage: buttonImageUrl ? `url(${buttonImageUrl})` : 'none', // Если URL изображения пустой, фон не отображается
                        backgroundSize: 'cover', // Растянуть изображение по размеру кнопки
                        backgroundPosition: 'center', // Центрирование изображения
                    }}
                >
                    {isChatVisible ? 'Скрыть чат' : 'Открыть чат'}
                </button>
            </div>


            <div className="settings-item">
                <label htmlFor="buttonImageUrl">URL изображения кнопки:</label>
                <input
                    type="text"
                    id="buttonImageUrl"
                    name="buttonImageUrl"
                    value={buttonImageUrl}
                    onChange={(e) => handleUrl(e.target.value)}
                    placeholder="Введите URL изображения"
                />
            </div>


            <div className="settings-item">
                <label htmlFor="backgroundColorSite">Цвет фона сайта:</label>
                <input
                    type="color"
                    id="backgroundColorSite"
                    name="backgroundColorSite"
                    value={colors.backgroundColorSite}
                    onChange={handleColorChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="backgroundColor">Цвет фона:</label>
                <input
                    type="color"
                    id="backgroundColor"
                    name="backgroundColor"
                    value={colors.backgroundColor}
                    onChange={handleColorChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="messageAreaBg">Цвет фона области сообщений:</label>
                <input
                    type="color"
                    id="messageAreaBg"
                    name="messageAreaBg"
                    value={colors.messageAreaBg}
                    onChange={handleColorChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="clientBubbleBg">Цвет фона сообщения клиента:</label>
                <input
                    type="color"
                    id="clientBubbleBg"
                    name="clientBubbleBg"
                    value={colors.clientBubbleBg}
                    onChange={handleColorChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="clientBubbleText">Цвет текста сообщения клиента:</label>
                <input
                    type="color"
                    id="clientBubbleText"
                    name="clientBubbleText"
                    value={colors.clientBubbleText}
                    onChange={handleColorChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="supportBubbleBg">Цвет фона сообщения поддержки:</label>
                <input
                    type="color"
                    id="supportBubbleBg"
                    name="supportBubbleBg"
                    value={colors.supportBubbleBg}
                    onChange={handleColorChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="supportBubbleText">Цвет текста сообщения поддержки:</label>
                <input
                    type="color"
                    id="supportBubbleText"
                    name="supportBubbleText"
                    value={colors.supportBubbleText}
                    onChange={handleColorChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="inputBorder">Цвет рамки ввода:</label>
                <input
                    type="color"
                    id="inputBorder"
                    name="inputBorder"
                    value={colors.inputBorder}
                    onChange={handleColorChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="buttonBg">Цвет фона кнопки:</label>
                <input
                    type="color"
                    id="buttonBg"
                    name="buttonBg"
                    value={colors.buttonBg}
                    onChange={handleColorChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="buttonBgHover">Цвет фона кнопки при наведении:</label>
                <input
                    type="color"
                    id="buttonBgHover"
                    name="buttonBgHover"
                    value={colors.buttonBgHover}
                    onChange={handleColorChange}
                />
            </div>


            <div className="settings-item">
                <label htmlFor="buttonHeight">Высота кнопки (vh):</label>
                <input
                    type="range"
                    id="buttonHeight"
                    name="buttonHeight"
                    min="5"
                    max="20"
                    value={parseFloat(colors.buttonHeight)}
                    onChange={handleRangeChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="buttonWidth">Ширина кнопки (vw):</label>
                <input
                    type="range"
                    id="buttonWidth"
                    name="buttonWidth"
                    min="4"
                    max="15"
                    value={parseFloat(colors.buttonWidth)}
                    onChange={handleRangeChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="buttonMargin">Отступ кнопки (px):</label>
                <input
                    type="range"
                    id="buttonMargin"
                    name="buttonMargin"
                    min="0"
                    max="50"
                    value={parseInt(colors.buttonMargin, 10)}
                    onChange={handleRangeChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="chatPrewHeight">Высота чата (vh):</label>
                <input
                    type="range"
                    id="chatPrewHeight"
                    name="chatPrewHeight"
                    min="20"
                    max="80"
                    value={parseFloat(colors.chatPrewHeight)}
                    onChange={handleRangeChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="chatPrewWidth">Ширина чата (vw):</label>
                <input
                    type="range"
                    id="chatPrewWidth"
                    name="chatPrewWidth"
                    min="20"
                    max="50"
                    value={parseFloat(colors.chatPrewWidth)}
                    onChange={handleRangeChange}
                />
            </div>

            <div className="settings-item">
                <label htmlFor="buttonOpenRadius">Скругление кнопки (%):</label>
                <input
                    type="range"
                    id="buttonOpenRadius"
                    name="buttonOpenRadius"
                    min="0"
                    max="80"
                    value={parseFloat(colors.buttonOpenRadius)}
                    onChange={handleRangeChange}
                />
            </div>

            <div className="refreshButton">
                <button
                    onClick={refreshColors}>
                    Сбросить цвета
                </button>
            </div>

            <div className="codeGenirator">
                <CodeGenirator/>
            </div>

        </div>
    );
};

export default ColorSettings;

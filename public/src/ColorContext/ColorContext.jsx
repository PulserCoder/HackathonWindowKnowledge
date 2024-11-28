import React, {createContext, useContext, useEffect, useState} from 'react';

// Контекст для цветов
const ColorContext = createContext();

// Провайдер контекста
export const ColorProvider = ({ children }) => {
    // Используем функцию для инициализации состояния, чтобы при загрузке брать данные из localStorage
    const defaultColors = {
        backgroundColorSite: '#c3bbbb',
        backgroundColor: '#f9f9f9',
        messageAreaBg: '#ffffff',
        messageShadow: '#000000',
        clientBubbleBg: '#007bff',
        clientBubbleText: '#ffffff',
        supportBubbleBg: '#e5e5e5',
        supportBubbleText: '#000000',
        inputBorder: '#cccccc',
        buttonBg: '#007bff',
        buttonBgHover: '#0056b3',
        fontFamily: 'Arial, sans-serif',
        buttonHeight: '5vh',
        buttonWidth: '5vw',
        buttonMargin: '10px',
        chatPrewHeight: '55vh',
        chatPrewWidth: '30vw',
        buttonOpenRadius: '10%',
        buttonUrl: 'none'
    };

    const [colors, setColors] = useState(() => {
        const savedColors = JSON.parse(localStorage.getItem('chatColors'));
        return savedColors || defaultColors
    });

    const normalizeColor = (color) => {
        if (color.startsWith('rgba')) {
            const rgba = color.match(/\d+/g).map(Number);
            return `#${((1 << 24) + (rgba[0] << 16) + (rgba[1] << 8) + rgba[2])
                .toString(16)
                .slice(1)}`;
        }
        return color; // Возвращаем оригинальный цвет, если нормализация не требуется
    };

    const applyColors = (colors) => {
        Object.keys(colors).forEach((key) => {
            const normalizedColor = normalizeColor(colors[key]);
            document.documentElement.style.setProperty(`--${key}`, normalizedColor);
        });
    };

    const refreshColors = () => {
        setColors(defaultColors); // Сбрасываем состояния
        applyColors(defaultColors); // Обновляем :root
        localStorage.setItem('chatColors', JSON.stringify(defaultColors)); // Обновляем localStorage
    };

    // Восстановление цветов из localStorage
    useEffect(() => {
        applyColors(colors);  // Применяем полученные цвета при монтировании
    }, [colors]); // При изменении цветов в состоянии, также обновляем их в :root

    // Сохранение цветов в localStorage при изменении
    useEffect(() => {
        localStorage.setItem('chatColors', JSON.stringify(colors));
    }, [colors]);

    return (
        <ColorContext.Provider value={{ colors, setColors, refreshColors }}>
            {children}
        </ColorContext.Provider>
    );
};

// Хук для использования контекста
export const useColors = () => useContext(ColorContext);

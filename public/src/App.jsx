import React from 'react';
import { ColorProvider } from "./ColorContext/ColorContext.jsx";
import './App.css';
import Chat from './Chat/Chat';
import ColorSettings from './ConfPage/ConfPage.jsx';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
    return (
        <ColorProvider>
            <Router>
                <Routes>
                    <Route path="/conf" element={<ColorSettings />} />
                    <Route path="/chat" element={<Chat />} />
                </Routes>
            </Router>
        </ColorProvider>
    );
}

export default App;

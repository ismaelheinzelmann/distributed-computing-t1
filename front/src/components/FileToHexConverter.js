import React, { useState } from 'react';
import { Button } from '@mui/material';

const FileToHexConverter = ({ userId }) => {
    const [fileInfo, setFileInfo] = useState({
        fileName: '',
        hexContent: ''
    });

    const handleFileInputChange = (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();

        reader.onload = async (e) => {
            const content = e.target.result;
            const hexContent = Array.from(new Uint8Array(content)).map(byte => byte.toString(16).padStart(2, '0')).join('');

            try {
                const response = await fetch('http://localhost:8080/repository/store-file', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ "id": userId, "fileName": file.name, "hexFileDump": hexContent})
                });

                if (response.ok) {
                    const responseData = await response.json();
                    alert(responseData.message)
                    setFileInfo({
                        fileName: file.name,
                        hexContent: hexContent
                    });
                } else {
                    alert('Storing file failed');
                }
            } catch (error) {
                console.error('Error storing file:', error);
                alert('Error storing file: ' + error.message);
            }
        };

        reader.readAsArrayBuffer(file);
    };

    return (
        <div>
            <input type="file" onChange={handleFileInputChange} style={{ display: 'none' }} id="fileInput" />
            <label htmlFor="fileInput">
                <Button variant="contained" component="span">
                    Upload File
                </Button>
            </label>
        </div>
    );
};

export default FileToHexConverter;

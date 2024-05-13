import React, { useState, useEffect } from 'react';
import { List, ListItem, ListItemText, ListItemIcon, IconButton, Button } from '@mui/material';
import { CloudDownload, Delete } from '@mui/icons-material';
import FileCopyIcon from '@mui/icons-material/FileCopy';
import FileToHexConverter from './FileToHexConverter';

const FileList = ({ userId }) => {
    const [files, setFiles] = useState([]);
    const [fileContent, setFileContent] = useState([])

    const fetchFilesFromRequest = async () => {
        try {
            const response = await fetch('http://localhost:8080/repository/files', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({"id": userId})
            });
            if (response.ok) {
                const responseData = await response.json();
                setFiles(responseData);
            } else {
                alert('Fetching files failed');
            }
        } catch (error) {
            console.error('Error fetching files:', error);
            alert(error.message);
        }
    };

    useEffect(() => {
        fetchFilesFromRequest();
    }, [userId]);

    const handleDownload = (filePath, fileName) => {
        fetch("http://localhost/" + filePath, {
            method: 'GET',
        })
        .then((response) => response.blob())
        .then((blob) => {
            const url = window.URL.createObjectURL(new Blob([blob]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
            link.parentNode.removeChild(link);
        });
    };

    const handleDelete = async (fileName) => {
        try {
            const response = await fetch('http://localhost:8080/repository/delete-file', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id: userId,
                    fileName: fileName
                })
            });
            if (response.ok) {
                // Remove the deleted file from state
                setFiles(prevFiles => prevFiles.filter(file => file.fileName !== fileName));
                alert('File deleted successfully');
            } else {
                const responseJson = await response.json();
                alert(responseJson.error);
            }
        } catch (error) {
            console.error('Error deleting file:', error);
            alert("Error deleting file");
        }
    };

    return (
        <div style={{ width: '80%', margin: '0 auto' }}>
            <div style={{display:"flex",justifyContent:"space-between", alignItems:"center"}}>
                <h2>List of Files</h2>
                <div style={{display:"flex", gap:"10px", alignItems:"center"}}>
                    <Button onClick={fetchFilesFromRequest} variant="contained" component="span">Refresh</Button>
                    <FileToHexConverter userId={userId}></FileToHexConverter>
                </div>
            </div>
            <List>
                {files.map((file, index) => (
                    <ListItem key={index} disableGutters>
                        <ListItemIcon>
                            <FileCopyIcon/>
                        </ListItemIcon>
                        <ListItemText primary={file.fileName} />
                        <div>
                            <IconButton onClick={(e) => handleDownload(file.filePath, file.fileName)} aria-label="download">
                                <CloudDownload />
                            </IconButton>
                            <IconButton onClick={(e) => handleDelete(file.fileName)} aria-label="delete">
                                <Delete />
                            </IconButton>
                        </div>
                    </ListItem>
                ))}
            </List>
        </div>
    );
};

export default FileList;

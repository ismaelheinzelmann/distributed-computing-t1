import './App.css';
import { BrowserRouter as Router, Link, Routes, Route } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import * as React from "react";
import LoginScreen from "./components/LoginScreen";
import FileList from "./components/FileList";


function App() {
    const [userId, setUserId] = React.useState(null);
    const [login, setLogin] = React.useState("");
    const [password, setPassword] = React.useState("");

    const handleChangeLogin = (e) => setLogin(e.target.value);
    const handleChangePassword = (e) => setPassword(e.target.value);
    const handleSubmitLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/user/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ login, password }),
            });
            if (response.ok) {
                const responseData = await response.json();
                // Assuming the server responds with a userId field
                const receivedUserId = responseData.message;
                setUserId(receivedUserId);
            } else {
                // Handle login failure
                alert('Login failed');
            }
        } catch (error) {
            alert(error.message);
        }
    }
    const handleSubmitSignup = async (e) => {
        e.preventDefault();
        try {
            console.log(userId, login, password)
            const response = await fetch('http://localhost:8080/user/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({login, password}),
            });
            if (response.ok) {
                alert('Signup Successfully!');
            } else {
                const responseJson = response.json()
                alert(responseJson.error);
            }
        } catch (error) {
            alert("Error");
        }
    }

    return (
        <div>
            <Box sx={{ flexGrow: 1 }}>
                <AppBar position="static">
                    <Toolbar sx={{display:"flex",justifyContent:"space-between"}}>
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                            Cloud File Storage
                        </Typography>
                        <Box>
                            {userId ? <Button color="inherit" onClick={()=>{
                                setUserId(null);
                            }}>Logout</Button>:<div></div>}
                        </Box>
                    </Toolbar>
                </AppBar>
            </Box>
            {userId ? <FileList userId={userId}/> : <LoginScreen
                handleLoginChange={handleChangeLogin}
                handlePasswordChange={handleChangePassword}
                handleSubmit={handleSubmitLogin}
                handleSignup={handleSubmitSignup}/>}

        </div>);

}

export default App;

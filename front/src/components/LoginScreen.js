import React from 'react';

const LoginScreen = ({handleLoginChange, handlePasswordChange, handleSubmit, handleSignup}) => {
    return (
        <div style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '100vh'
        }}>
            <div style={{
                textAlign: 'center',
                padding: '20px',
                border: '1px solid #ccc',
                borderRadius: '5px',
                backgroundColor: '#f9f9f9',
                boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)'
            }}>
                <h2>Login</h2>
                <input type="text" style={inputStyle} onChange={(e) => handleLoginChange(e)} placeholder="Username" />
                <input type="password" style={inputStyle} onChange={(e) => handlePasswordChange(e)} placeholder="Password" />
                <div style={{ marginTop: '20px' }}>
                    <button style={buttonStyle} onClick={(e) => handleSubmit(e)}>Login</button>
                    <button style={buttonStyle} onClick={(e) => handleSignup(e)}>Signup</button>
                </div>
            </div>
        </div>
    );
};

const inputStyle = {
    width: '100%',
    padding: '10px',
    marginBottom: '10px',
    borderRadius: '3px',
    border: '1px solid #ccc',
    boxSizing: 'border-box'
};

const buttonStyle = {
    padding: '10px 20px',
    marginRight: '10px',
    border: 'none',
    borderRadius: '3px',
    cursor: 'pointer'
};

export default LoginScreen;

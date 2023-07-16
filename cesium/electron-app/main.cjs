'use strict'
const { app, BrowserWindow } = require('electron');
const waitOn = require('wait-on');
const { spawn } = require('child_process');

function createWindow() {
  const mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false
    }
  });

  mainWindow.loadURL('http://localhost:8081/veroviz');

  mainWindow.on('closed', function () {
    app.quit();
  });
}

app.on('ready', async () => {
  spawn('node', ['server.js']); // Replace with the path to your server file

  // Wait for the server to become available
  await waitOn({ resources: ['http://localhost:8081/veroviz'] }); // Replace with your Node server URL

  createWindow();
});

app.on('will-quit', () => {
  // Close the server process when the Electron app is quitting
  
});

app.on('window-all-closed', function () {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', function () {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow();
  }
});

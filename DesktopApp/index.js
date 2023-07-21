const { app, BrowserWindow, ipcMain } = require('electron');

function createWindow() {
  const mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false
    }
  });

  mainWindow.loadURL('https://veroviz.org/sketch.html');

  mainWindow.webContents.on('did-finish-load', () => {
    mainWindow.webContents.executeJavaScript(`
    const para = document.createElement("button");
    para.innerText = "Done";  
    document.getElementById('leftMenuDiv').appendChild(para)
    para.classList.add('leftMenuBtnDiv')
    para.addEventListener('click',()=>{
      let data={
        data:document.getElementById('bbox').value
      }
      console.log(data)
      fetch('http://localhost:5000/init-data', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      })
        .then(response => response.json())
        .then(result => {
          console.log('Response:', result);
          // Handle the response data
        })
        .catch(error => {
          console.error('Error:', error);
          // Handle the error
        });
  })
    `);
  });

  mainWindow.on('closed', function () {
    app.quit();
  });
}

app.on('ready', createWindow);

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

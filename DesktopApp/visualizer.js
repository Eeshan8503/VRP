const { app, BrowserWindow } = require('electron')

export default function visualizer(){
    function createWindow() {
        const mainWindow = new BrowserWindow({
          width: 800,
          height: 600,
          webPreferences: {
            nodeIntegration: true
          }
        })
      
}
  mainWindow.loadURL('https://veroviz.org/sketch.html')

  // Uncomment the following line to open the DevTools
  // mainWindow.webContents.openDevTools()

  mainWindow.on('closed', function () {
    app.quit()
  })
}

app.on('ready', createWindow)

app.on('window-all-closed', function () {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', function () {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow()
  }
})

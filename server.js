var io = require('socket.io').listen(9000);

io.sockets.on('connection', function (socket) {
  function reportDate(){
    var d = new Date();
    socket.emit('myevent', { 
      text: 'date is this' , 
      date: d.toUTCString(),
      millis: d.getUTCMilliseconds()
    });
    setTimeout(reportDate,100);
  }
  setTimeout(reportDate,100);
  socket.on('myclientevent', function (data) {
    console.log(data);
  });
});

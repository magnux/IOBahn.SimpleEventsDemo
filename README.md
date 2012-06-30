IOBahn.SimpleEventsDemo
=======================

Demo of [IOBahn](https://github.com/magnux/IOBahn) library. Based on Simple PubSub demo from AutobahnAndroid library.

Many thanks to the [Socket.IO](http://socket.io/) and [Autobahn](http://autobahn.ws/) projects.

## Overview

This demo will connect to your Socket.IO demo server, which will send messages to your phone every 5000 millisecons (by default, configurable).
When the message is received the client will reply another message, that you can see in the server side.

## How to use it?

###The client
Simply download the whole repo and import it in eclipse. You must have Android plugin installed and well configured.

###The server
Is the server.js file, simply run: node server.js. You must have node.js and socket.io in order to run it. 

##License
(The MIT License)
    
    Copyright (c) 2012 Alejandro Hernandez
    
    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the 'Software'), to deal in
    the Software without restriction, including without limitation the rights to use,
    copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
    Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
    COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
    IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
    CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
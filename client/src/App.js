import React, { useState } from "react";
import Home from './components/Home';
import UserLogin from './components/UserLogin';
import './App.css';


import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";

import { Button, Navbar } from "react-bootstrap";


export default function App() {
  const [userId, setUserId] = useState("");
  const [userName, setUserName] = useState("");


  return (
    <Router>
      <Navbar>
        <Link to="/">
          <Navbar.Brand>CoinHub</Navbar.Brand>
        </Link>
        <Navbar.Toggle />
        <Navbar.Collapse className="justify-content-end">
          {userId.length === 0 
            ?   <Link to="/login">
                  <Button>Login</Button>
                </Link>
            : <p>@{userName}</p>
          }
        </Navbar.Collapse>
      </Navbar>
      <div className="body">


        <Switch>
          <Route path="/login" >
            <UserLogin setuserid={setUserId} setusername={setUserName}/>
          </Route>
          <Route path="/">
            <Home />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}


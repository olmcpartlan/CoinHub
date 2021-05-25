import React, { useState } from "react";
import Home from './components/Home';
import UserLogin from './components/UserLogin';
import './App.css';

import { useHistory } from 'react-router-dom'; 

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";

import { Button, Navbar } from "react-bootstrap";


export default function App() {

  let history = useHistory();

  function updateHistory() {
    console.log("should update history here.")
  }


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
            ? (
              <Link to="/login">
                <Button>Login</Button>
              </Link>
            ) 
            : ( <p>@{userName}</p>)
          }
        </Navbar.Collapse>
      </Navbar>
      <div className="body">
        <Switch>
          <Route path="/login">
            <UserLogin setuserid={setUserId} setusername={setUserName} />
          </Route>
          <Route path="/">
            <Home
              // i don't think either of these work lmao.
              updateHistory={updateHistory}
              history={history}
            />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}


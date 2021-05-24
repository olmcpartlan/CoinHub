import React, { Component } from 'react';
import { Button } from 'react-bootstrap';

/*

- User registration / login
  - Hooks up to Coinbase account.
  - display user's current coins and transactinos <- MVP
      - Java server validates users.
      - Homgo db? MySQL?


*/


export default class Home extends Component {
  render() {
    return (
      <div>
        <p>Home Compnent.</p>
        <Button>a button</Button>
      </div>

    )
  }
}
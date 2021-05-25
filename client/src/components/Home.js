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
  constructor(props) {
    super(props);

  }

  render() {
    return (
      <div>
        <p>Home Compnent.</p>
        <Button onClick={this.props.updateHistory}>a button</Button>
      </div>

    )
  }
}
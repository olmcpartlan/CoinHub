import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'; 
import { far, faTimesCircle } from '@fortawesome/free-solid-svg-icons';


export default class UserLogin extends Component {
  render() {
    return (
      <div>
        <Container>
          <Row className="login-header">
            <p >Let's Get Started</p>
          </Row>
          <Row>
            <Login/>
            <Register/>
          </Row>
        </Container>
      </div>
    );
  }
}

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      input_userName: "",
      input_pass: "",
    }
  }


  render() {
    return (
      <Col className="login-col">
        <h4>Login</h4>
        <InputGroup className="mb-3">
          <InputGroup.Prepend>
            <InputGroup.Text id="basic-addon1">@</InputGroup.Text>
          </InputGroup.Prepend>
          <FormControl
            placeholder="Username"
            aria-label="Username"
            aria-describedby="basic-addon1"
          />
        </InputGroup>
        <InputGroup>
          <FormControl
            placeholder="Password"
            aria-label="Password"
            aria-describedby="basic-addon1"
          />
        </InputGroup>

        <Button className="login-button" variant="primary" >
          Login
        </Button>
      </Col>
    );
  }

            
}

class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      input_email: "",
      input_first: "",
      input_last: "",
      input_pass: "",
      emailValidationMsg: "Please enter a valid Email address.",
      // TODO: There's probably a better way of doing this, but this works for now.
      showEmailValidation: false,
      showFirstNameValidation: false,
      showLastNameValidation: false,
      showPasswordValidation: false,
      showConfPassValidation: false,
      registrationDisabled: true
    }
  }


  submit = () => {
    let user = JSON.stringify({
        firstName:  this.state.input_first,
        lastName:   this.state.input_last,
        email:      this.state.input_email,
        pass:       this.state.input_pass,
    });

    console.log(user);

    fetch("/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: user
    }) 
      .then(res => res.text())
      .then(res => {
        console.log(res);
      })

  }

  validateEmail = (e) => {
    this.setState({showEmailValidation: true})

    if(e.target.value.includes("@"))
    {
      if(e.target.value.includes("."))
      {
        this.setState({ 
          showEmailValidation: false,
          input_email: e.target.value,
          registrationDisabled: false
        })

        // If the email meets validation requirements, then send a request checking if it's already in use.
      }

      else if(e.target.value !== "") this.setState({
        showEmailValidation: true,
        registrationDisabled: true,
      })

    }
    else if(e.target.value !== "") this.setState({
      showEmailValidation: true,
      registrationDisabled: true,
    })

  }

  validateFirstName = (e) => {
    this.setState({
      input_first: e.target.value,
      showFirstNameValidation: e.target.value.length > 3 ? false : true
    })
  }

  validateLastName = (e) => {
    this.setState({
      input_last: e.target.value,
      showLastNameValidation: e.target.value.length > 3 ? false : true
    })
  }

  validatePassword = (e) => {
    this.setState({
      input_pass: e.target.value,
      showPasswordValidation: e.target.value.length > 3 ? false : true
    })
  }

  validateConfirmPassword = (e) => {
    // Confirm password validation is just going to check that e.target.value matches this.state.input_pass
  }

  render() {
    return (
      <Col className="register-col">
        <h4>Register</h4>
        <InputGroup className="mb-3">
          { this.state.showEmailValidation &&
            <FontAwesomeIcon icon={faTimesCircle} color='red'/>
          }
          <FormControl
            placeholder="Your Email"
            aria-label="EmailAddress"
            aria-describedby="basic-addon1"
            onBlur={this.validateEmail}
          />
        </InputGroup>
        <InputGroup className="mb-3">
          <FormControl
            placeholder="First Name"
            aria-label="FirstName"
            aria-describedby="basic-addon1"
            onChange={this.validateFirstName}
          />

          <FormControl
            placeholder="Last Name"
            aria-label="LastName"
            aria-describedby="basic-addon1"
            onChange={this.validateLastName}
          />
        </InputGroup>
        <InputGroup className="mb-3">
          <FormControl
            placeholder="Password"
            aria-label="Password"
            aria-describedby="basic-addon1"
            onChange={this.validatePassword}
          />
        </InputGroup>
        <InputGroup className="mb-3">
          <FormControl
            placeholder="Confirm Password"
            aria-label="Confirm Password"
            aria-describedby="basic-addon1"
            onChange={this.validateConfirmPassword}
          />
        </InputGroup>
        <Button 
          className="login-button" 
          variant="success" 
          disabled={this.state.registrationDisabled}
          onClick={this.submit} 
        >
          Register
        </Button>
      </Col>
    );
  }
}
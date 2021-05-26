import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'; 
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { Link, withRouter } from 'react-router-dom';
import ValidationOverlay from './ValidationOverlay';



class UserLogin extends Component {
  render() {
    return (
      <div>
        <Container>
          <Row className="login-header">
            <p >Let's Get Started</p>
          </Row>
          <Row>
            <Login 
              setuserid={this.props.setuserid} 
              setusername={this.props.setusername} 
              history={this.props.history}
            />

            <Register
              setuserid={this.props.setuserid} 
              setusername={this.props.setusername} 
              history={this.props.history}
            />

          </Row>
        </Container>
      </div>
    );
  }
}

// IN ORDER TO NAVIGATE PROGRAMATICALLY, THIS IS REQUIRED.
export default withRouter(UserLogin);

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      showUsernameValidation: false,
      showPasswordValidation: false,
      userLoggedIn: false,
      userNameValidationMessage: "",
      passValidationMessage: "",
      submitEnabled: false
    }
  }

  validateUsername = (e) => {
    this.setState({
      username: e.target.value,
      showUsernameValidation: e.target.value.length > 3 ? false : true,
      userNameValidationMessage: e.target.value.length > 3 
        ? "User not found in our records."
        : ""
    })
  }

  validatePassword = (e) => {
    this.setState({
      password: e.target.value,
      showPasswordValidation: e.target.value.length > 3 ? false : true
    })

  }

  submit = (e) => {
    fetch("/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userName: this.state.username,
        pass: this.state.password,
      }),
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res);

        // Need to set the route from this point, AFTER the correct user has been confirmed.
        if (res["userName"] === "NO_USER_FOUND") {
          this.setState({
            passValidationMessage: "Password did not match our records. Would you like to reset it? ",
            showPasswordValidation: true,
          });
        } 
        else {
          this.setState({ userLoggedIn: true });
          this.props.setuserid(res["userId"]);
          this.props.setusername(res["userName"]);

          this.props.history.push("/");
        }
      });
  }

  render() {
    return (
      <Col className="login-col" md="auto">
        <h4>Login</h4>

        {/* LOGIN USER NAME */}

        <InputGroup className="mb-3">
          {/* This is getting a bit cumbeersome, could create separate comp with props for the message. */}
          <InputGroup.Prepend>
            {this.state.showUsernameValidation && (
              <InputGroup.Prepend>
                <ValidationOverlay
                  validationMessage={this.state.userNameValidationMessage}
                />
              </InputGroup.Prepend>
            )}

            {this.state.showUsernameValidation || (
              <InputGroup.Text className="input-icon" id="basic-addon1">
                🙋‍♂️
              </InputGroup.Text>
            )}
          </InputGroup.Prepend>
          <FormControl
            placeholder="Username"
            aria-label="Username"
            aria-describedby="basic-addon1"
            onBlur={this.validateUsername}
          />
        </InputGroup>

        {/* LOGIN PASSWORD */}

        <InputGroup>
          <InputGroup.Prepend>
            {this.state.showPasswordValidation && (
              <InputGroup.Prepend>
                <InputGroup.Text>
                  <FontAwesomeIcon icon={faTimesCircle} color="red" />
                </InputGroup.Text>
              </InputGroup.Prepend>
            )}
          </InputGroup.Prepend>

          {this.state.showPasswordValidation || (
            <InputGroup.Text className="input-icon" id="basic-addon1">
              🔑
            </InputGroup.Text>
          )}

          <FormControl
            placeholder="Password"
            aria-label="Password"
            type="password"
            aria-describedby="basic-addon1"
            onBlur={this.validatePassword}
          />
        </InputGroup>

        <Link to={this.state.userLoggedIn ? "/" : "/login"}>
          <Button
            className="login-button"
            disabled={!this.state.submitEnabled}
            onClick={this.submit}
            variant="primary"
          >
            Login
          </Button>
        </Link>
      </Col>
    );
  }
}

class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      input_userName: "",
      input_email: "",
      input_pass: "",
      emailValidationMsg: "Please enter a valid Email address.",
      showEmailValidation: false,
      showPasswordValidation: false,
      showConfPassValidation: false,
      registrationDisabled: true
    }
  }


  submit = () => {
    fetch("/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        userName:   this.state.input_userName,
        email:      this.state.input_email,
        pass:       this.state.input_pass,
      }) 
    }) 
      .then(res => res.json())
      .then(res => {

        console.log(res);

        this.props.setuserid(res["userId"]);
        this.props.setusername(res["userName"]);
        this.props.history.push("/");
      })
  }

  validateEmail = (e) => {
    this.setState({showEmailValidation: true})

    if(e.target.value.includes("@") && e.target.value.includes("."))
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

  validatePassword = (e) => {
    this.setState({
      input_pass: e.target.value,
      showPasswordValidation: e.target.value.length > 3 ? false : true
    })
  }

  validateConfirmPassword = (e) => {
    // Confirm password validation is just going to check that e.target.value matches this.state.input_pass
  }

  validateUsername = (e) => {
    this.setState({ 
      input_userName: e.target.value,
      showUsernamevalidation: e.target.value.length > 3 ? false : true
    })
  }

  render() {
    return (
      <Col className="register-col" md="auto">
        <h4>Register</h4>
        <InputGroup className="mb-3">
          <InputGroup.Prepend>
            <InputGroup.Text id="basic-addon1">@</InputGroup.Text>
          </InputGroup.Prepend>
          <FormControl
            placeholder="Username"
            aria-label="Username"
            aria-describedby="basic-addon1"
            onChange={this.validateUsername}
          />
        </InputGroup>
        <InputGroup className="mb-3">
          {this.state.showEmailValidation && (
            <InputGroup.Prepend>
              <InputGroup.Text>
                <FontAwesomeIcon icon={faTimesCircle} color="red" />
              </InputGroup.Text>
            </InputGroup.Prepend>
          )}
          <FormControl
            placeholder="Your Email"
            aria-label="EmailAddress"
            aria-describedby="basic-addon1"
            onBlur={this.validateEmail}
          />
        </InputGroup>
        <InputGroup className="mb-3">
          <FormControl
            placeholder="Password"
            aria-label="Password"
            type="password"
            aria-describedby="basic-addon1"
            onChange={this.validatePassword}
          />
        </InputGroup>
        <InputGroup className="mb-3">
          <FormControl
            placeholder="Confirm Password"
            aria-label="Confirm Password"
            type="password"
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
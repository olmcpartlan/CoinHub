import logo from './logo.svg';
import './App.css';
import Home from './components/Home';
import UserLogin from './components/UserLogin';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <UserLogin/>
      </header>
    </div>
  );
}

export default App;

import React, { Component } from 'react';
import AutocompleteDropDown from './component/AutocompleteDropDown'
import {Button, Alert} from 'react-bootstrap'
import axios from 'axios'
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';



class App extends Component {

    constructor() {
        super()
        this.state = {
            currency: '',
            excurrency: '',
            amount: '',
            result: ''
        }
    }
    

    changeCurrency(currency) {
        this.setState({ currency: currency })
    }

    changeExcurrency(excurrency) {
        this.setState({ excurrency: excurrency })
    }

    handleAmountChange(event) {
        this.setState({ amount: event.target.value})
    }

    getExchangeAmount(){
        var url = 'http://localhost:8080/excurrency?currency='+this.state.currency+
            '&exCurrency='+this.state.excurrency+'&amount='+this.state.amount 
      
          axios.get(url)
          .then(response => {
              // success
              this.setState({result: response.data})
          })
          .catch((error) => {
              // handle this error
              console.log('error: '+error);
          })
      }

    render() {
                        
        return (
            <div className="App">
               <Alert className="alart" variant="success">
                <h1>Currency converter</h1>
                </Alert>
                <AutocompleteDropDown
                    data={
                        {
                            changeCurrency: this.changeCurrency.bind(this),
                            changeExcurrency: this.changeExcurrency.bind(this),
                            currecyType:'locale',
                            placeholderText: 'local currency'
                        }
                    }
                    suggestions={
                        currecyData
                    }
                />
                <br /><br />
                <AutocompleteDropDown
                    data={
                        {
                            changeCurrency: this.changeCurrency.bind(this),
                            changeExcurrency: this.changeExcurrency.bind(this),
                            currecyType:'exchange',
                            placeholderText: 'exchange currency'
                        }
                    }
                    suggestions={
                        currecyData
                    }
                />
                <br/><br/>
                <input id='amount' type='text' placeholder='amount in digit'  
                onChange={(e) => this.setState({amount: e.target.value})}></input>  
                <br/><br/>
                 <Button onClick={() => {this.getExchangeAmount()}  } variant="primary">Show exchanged amount</Button>
                 <br/><br/>
                <textarea  className={"textarea"}  value={this.state.result} readOnly/>
            </div>
        );
    }
}

export default App;

const currecyData = [
    "AED",
    "ARS",
    "AUD",
    "BGN",
    "BRL",
    "BSD",
    "CAD",
    "CHF",
    "CLP",
    "CNY",
    "COP",
    "CZK",
    "DKK",
    "DOP",
    "EGP",
    "EUR",
    "FJD",
    "GBP",
    "GTQ",
    "HKD",
    "HRK",
    "HUF",
    "IDR",
    "ILS",
    "INR",
    "ISK",
    "JPY",
    "KRW",
    "KZT",
    "MXN",
    "MYR",
    "NOK",
    "NZD",
    "PAB",
    "PEN",
    "PHP",
    "PKR",
    "PLN",
    "PYG",
    "RON",
    "RUB",
    "SAR",
    "SEK",
    "SGD",
    "THB",
    "TRY",
    "TWD",
    "UAH",
    "USD",
    "UYU",
    "ZAR",
]


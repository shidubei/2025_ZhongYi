import React,{useState} from 'react'
import axios from 'axios'

export default function CoinInput(){
    // store the user input
    const [formData,setFormData] = useState({
        target: '',
        coinList: ''
    });

    const [errorInfo,setErrorInfo] = useState('')

    const [result,setResult] = useState('')

    // change user input
    const handleFormDataChange = (event) =>{
        const {name,value} = event.target
        setFormData({
            ...formData,
            [name]: value
        });
    };

    // Validate coinList to ensure only numbers and commas are present
    const validateCoinList = (coinList) => {
        const regex = /^(\d+(\.\d{1,2})?)(,\d+(\.\d{1,2})?)*$/; // Regex for numbers separated by commas, allowing up to 2 decimal places
        return regex.test(coinList);
    };

    // Validate if target is a valid number
    const validateTarget = (target) => {
        const regex = /^\d+(\.\d{1,2})?$/; // Regex for positive numbers with up to 2 decimal places
        return regex.test(target);
    };

    // send to back-end
    const handleSubmit = async (event) =>{
        event.preventDefault();

        // Validate target
        if (!validateTarget(formData.target)) {
            setErrorInfo('Invalid Target: Please enter a number between 0 and 10000, allowing up to 2 decimal places.');
            return;
        }

        // Validate coinList
        if (!validateCoinList(formData.coinList)) {
            setErrorInfo('Invalid Coin List: Please enter numbers separated by commas, allowing up to 2 decimal places.');
            return;
        }

        // Convert coinList from comma-separated string to an array of numbers
        const coinListArray = formData.coinList
        .split(',')
        .map(item => parseFloat(item.trim()))
        .filter(item => !isNaN(item)); // Filter out invalid values

        const sendFormData = {
        ...formData,
        coinList: coinListArray
        };


        try{
            // send Post Request
            const response = await axios.post("http://18.138.192.230:80/Coin-game",sendFormData,{withCredentials:true});
            // handle back-data
            if(response.status === 200){
                setResult(JSON.stringify(response.data));
                setErrorInfo('');
            }else if(response.status === 400){
                setErrorInfo('Your Input Are Invalid')
            }
        }catch(error){
            setErrorInfo(error.response?.data);
        }

    }

return (
    <div style={{ padding: '20px', maxWidth: '500px', margin: 'auto' }}>
      <h2>Coin Target Game</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="target" style={{ display: 'block', marginBottom: '5px' }}>
            Target:
          </label>
          <input
            type="text"
            id="target"
            name="target"
            value={formData.target}
            onChange={handleFormDataChange}
            placeholder='Input the number from 1-10000,only allow 2 decimal places'
            required
            style={{ width: '100%', padding: '8px' }}
          />
        </div>

        <div style={{ marginBottom: '10px' }}>
          <label htmlFor="coinList" style={{ display: 'block', marginBottom: '5px' }}>
            Coin List (comma-separated):
          </label>
          <input
            type="text"
            id="coinList"
            name="coinList"
            value={formData.coinList}
            onChange={handleFormDataChange}
            placeholder="Input the coins,from [0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000]"
            required
            style={{ width: '100%', padding: '8px' }}
          />
        </div>

        <button
          type="submit"
          style={{
            backgroundColor: '#4CAF50',
            color: 'white',
            padding: '10px 20px',
            border: 'none',
            cursor: 'pointer',
            width: '50%',
          }}
        >
          Submit
        </button>
      </form>

        {errorInfo && (
            <div style={{ marginTop: '20px', color: 'red' }}>
            <strong>Error:</strong> {JSON.stringify(errorInfo)}
            </div>
        )}

        {result && !errorInfo && (
            <div style={{ marginTop: '20px', color: 'green', whiteSpace: 'pre-wrap', wordWrap: 'break-word' }}>
                <strong>Result:</strong>
                <div>{result}</div>
            </div>
        )}

    </div>
  );
}
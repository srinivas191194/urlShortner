import axios from "axios";
import React, { useState } from 'react';
function App() {
  const[link, setLink] = useState("");
  const  calling = (event) => {
    event.preventDefault()
    let variable = document.getElementById("inputfield").value
    console.log(variable)
    axios.post("http://localhost:8080/longurl/",{"url": variable})
         .then(function(responce){console.log(responce);  setLink(responce.data)})
         .catch(function(error){console.log(error)})
    }

  return (
    <div className="App">
     <h1>welcome to react</h1>
     <form method = "post" onSubmit= {calling}>
      <label>
        Enter URL:
        <input type="text" id = "inputfield" name="name" placeholder ="url" required />
      </label>
        <input type="submit" value="Submit" />
     </form>
     <a href = {link}>{link}</a>
    </div>
  );
}

export default App;

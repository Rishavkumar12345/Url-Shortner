import { createContext, useContext, useState } from "react"

const contextApi=createContext();

export const ContextProvider=({children})=>{

    const getToken=localStorage.getItem("JWT_TOKEN")
                    ? JSON.parse(localStorage.getItem("JWT_TOKEN"))
                    : null;
                
    const [token, setToken]=useState(getToken);

    const sendData={
        token,
        setToken
    };

    return <contextApi.Provider value={sendData}>{children}</contextApi.Provider>
};

export const useStoreContext=()=>{
    const context=useContext(contextApi);
    return context;
}
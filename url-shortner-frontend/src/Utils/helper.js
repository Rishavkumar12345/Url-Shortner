import { subDomainList } from "./constant";

export const getapp=()=>{
    const subdomain=getsubDomain(window.location.hostname);

    const mainApp=subDomainList.find((app)=>app.main);

    if(subdomain==="") return mainApp.app;

    const apps=subDomainList.find((app)=>subdomain=app.subdomain);

    return apps ? apps.app : mainApp.app;
}


export const getsubDomain=(location)=>{

    const locationPart=location.split(".");
    const isLocalHost=locationPart.slice(-1)[0] === "localhost";
    const sliceTill=isLocalHost ? -1 : -2;
    return locationPart.slice(0,sliceTill).join("");
}
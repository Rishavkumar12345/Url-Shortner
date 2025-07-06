import {useQuery} from 'react-query';
import api from '../api/api';

export const useFetchMyShortenUrl=(token,onError)=>{
    return useQuery("myShortenUrl",
        async ()=>{
            return await api.get("/api/url/allUrl",
                {
                    headers:{
                        "Content-Type":"application/json",
                        Accept:"application/json",
                        Authorization:"Bearer "+token
                    }
                }
            );
        },{
            select:(data)=>{
     
                const shotenUrl = data.data.sort(
                    (a,b)=> new Date(b.createdDate) - new Date(a.createdDate)
                )
                
                return shotenUrl;
            },
            onError,
            staleTime: 5000
        });
}

export const useFetchTotalClick=(token,onError)=>{
    return useQuery("url-fetchQuery",
        async ()=>{
            return await api.get("/api/url/totalclicks?startDate=2025-07-05&endDate=2025-07-29",
                {
                    headers:{
                        "Content-Type":"application/json",
                        Accept:"application/json",
                        Authorization:"Bearer "+token
                    }
                }
            );
        },{
            select:(data)=>{
                 // data.data =>
                    //  {
                    //     "2024-01-01": 120,
                    //     "2024-01-02": 95,
                    //     "2024-01-03": 110,
                    //   };
                      
                const convertToArray = Object.keys(data.data).map((key) => ({
                    clickDate: key,
                    count: data.data[key], // data.data[2024-01-01]
                }));
                // Object.keys(data.data) => ["2024-01-01", "2024-01-02", "2024-01-03"]

                // FINAL:
                //   [
                //     { clickDate: "2024-01-01", count: 120 },
                //     { clickDate: "2024-01-02", count: 95 },
                //     { clickDate: "2024-01-03", count: 110 },
                //   ]
                return convertToArray;
            },
            onError,
            staleTime: 5000
        });
};
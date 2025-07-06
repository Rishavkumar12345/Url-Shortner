import React, { useState } from 'react'
import { useStoreContext } from '../../contextapi/contextapi';
import { useForm } from 'react-hook-form';
import TextField from '../TextField';
import { RxCross2 } from "react-icons/rx";
import Tooltip from '@mui/material/Tooltip';
import api from '../../api/api';
import toast from 'react-hot-toast';

const CreateNewShorten = ({setOpen,refetch}) => {

    const {token}=useStoreContext();
    const [loading,setLoading]=useState(false);

    
    const {register,handleSubmit,reset, formState:{errors}}=useForm({
        defaultValues:{
            orginalUrl:"",
            
        },
        mode: "onTouched",
    });

    const createShortUrlHandler=async(data)=>{
        setLoading(true);
        try{
            const {data : res}=await api.post(
                "/api/url/shortner",
                data,
                {
                    headers:{
                        "Content-Type":"application/json",
                        Accept:"application/json",
                        Authorization:"Bearer "+token
                    }
                }
            );
            
            const shortUrl= `${import.meta.env.VITE_REACT_SUBDOMAIN}/${res.shortUrl}`
            navigator.clipboard.writeText(shortUrl).then(()=>{
                toast.success("short Url copied to clipboard",{
                    position:"bottom-center",
                    className:"mb-5",
                    duration:3000
                });
            });
            reset();
            setOpen(false)
        } catch (e) {
            console.log(e.message);
            toast.error("Url Creation Fail!");

        } finally{
            setLoading(false)
        }
    };

  return (
    <form
        onSubmit={handleSubmit(createShortUrlHandler)}
        className="sm:w-[450px] w-[360px] relative bg-white shadow-custom pt-8 pb-5 sm:px-8 px-4 rounded-lg"
      >

        <h1 className="font-montserrat sm:mt-0 mt-3 text-center  font-bold sm:text-2xl text-[22px] text-slate-800 ">
                Create New Shorten Url
        </h1>

        <hr className="mt-2 sm:mb-5 mb-3 text-slate-950" />
        <div>
          <TextField
            label="Enter URL"
            required
            id="originalUrl"
            placeholder="https://example.com"
            type="url"
            message="Url is required"
            register={register}
            errors={errors}
          />
        </div>
        <button
          className="bg-customRed font-semibold text-white w-32  bg-custom-gradient  py-2  transition-colors  rounded-md my-3"
          type="text"
        >
          {loading ? "Loading..." : "Create"}
        </button>

        {!loading && (
          <Tooltip title="Close">
            <button
              disabled={loading}
              onClick={() => setOpen(false)}
              className=" absolute right-2 top-2  "
            >
              <RxCross2 className="text-slate-800   text-3xl" />
            </button>
          </Tooltip>
        )}

    </form>
  )
}

export default CreateNewShorten
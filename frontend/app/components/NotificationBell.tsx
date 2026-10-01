"use client";
import {useEffect,useState} from "react";
const API=process.env.NEXT_PUBLIC_API_URL;
type N={id:number;title:string;message?:string;linkUrl?:string;readFlag:boolean;createdAt:string};
export default function NotificationBell(){
 const [open,setOpen]=useState(false),[items,setItems]=useState<N[]>([]),[unread,setUnread]=useState(0);
 const token=()=>typeof window==="undefined"?"":localStorage.getItem("rkgitm_token")||sessionStorage.getItem("rkgitm_token")||"";
 async function load(){if(!API||!token())return;const r=await fetch(API+"/api/notifications",{headers:{Authorization:"Bearer "+token()}});if(r.ok){const d=await r.json();setItems(d.items||[]);setUnread(d.unread||0);if((d.unread||0)>unread)setOpen(true)}}
 useEffect(()=>{void load();const id=window.setInterval(()=>void load(),30000);return()=>window.clearInterval(id)},[]);
 useEffect(()=>{if(!open)return;const id=window.setTimeout(()=>setOpen(false),6000);return()=>window.clearTimeout(id)},[open]);
 async function markAll(){if(!API)return;await fetch(API+"/api/notifications/read-all",{method:"PATCH",headers:{Authorization:"Bearer "+token()}});setUnread(0);setItems(v=>v.map(x=>({...x,readFlag:true})))}
 return <div className="notifWrap"><button className="notifBell" type="button" aria-label="Notifications" onClick={()=>{setOpen(!open);if(!open)void load()}}>🔔{unread>0&&<b>{unread>9?"9+":unread}</b>}</button>{open&&<div className="notifPanel"><div className="notifHead"><strong>Notifications</strong>{unread>0&&<button onClick={()=>void markAll()}>Mark all read</button>}</div>{items.length?items.slice(0,8).map(n=><a key={n.id} className={!n.readFlag?"unread":""} href={n.linkUrl||"#"}><strong>{n.title}</strong><span>{n.message}</span><small>{new Date(n.createdAt).toLocaleString()}</small></a>):<p>No notifications yet.</p>}</div>}</div>
}
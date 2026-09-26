"use client";
import {useEffect,useState} from "react";
const API=process.env.NEXT_PUBLIC_API_URL||"http://localhost:8080";
type A={id:number;title:string;studentName:string;department?:string;category?:string;description?:string;proofUrl?:string};
export default function Faculty(){const [items,setItems]=useState<A[]>([]);const [msg,setMsg]=useState("");
async function load(){try{const r=await fetch(API+"/api/achievements/pending");setItems(await r.json())}catch{setMsg("Backend unavailable.")}}
useEffect(()=>{void load()},[]);
async function decide(id:number,value:"APPROVED"|"REJECTED"){await fetch(API+`/api/achievements/${id}/status?value=${value}`,{method:"PATCH"});load()}
return <main className="dash"><a href="/">← Public Hub</a><p className="eyebrow">FACULTY REVIEW • MVP</p><h1>Verification queue</h1><p>Review submitted evidence before publishing any achievement.</p>{msg&&<p>{msg}</p>}<div className="queue">{items.length===0&&!msg&&<div className="empty">No pending submissions.</div>}{items.map(a=><article key={a.id}><div><span>{a.category||"Achievement"} • {a.department||"Department not provided"}</span><h3>{a.title}</h3><strong>{a.studentName}</strong><p>{a.description}</p>{a.proofUrl&&<a href={a.proofUrl} target="_blank">Open proof ↗</a>}</div><div className="reviewActions"><button onClick={()=>decide(a.id,"APPROVED")}>Approve</button><button className="reject" onClick={()=>decide(a.id,"REJECTED")}>Reject</button></div></article>)}</div></main>}
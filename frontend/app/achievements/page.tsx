"use client";
import {useEffect,useState} from "react";
const API=process.env.NEXT_PUBLIC_API_URL||"http://localhost:8080";
type A={id:number;title:string;studentName:string;department?:string;category?:string;description?:string};
export default function Achievements(){const [items,setItems]=useState<A[]>([]);useEffect(()=>{fetch(API+"/api/achievements/public").then(r=>r.json()).then(setItems).catch(()=>{})},[]);
return <main className="dash"><a href="/">← Achievement Hub</a><p className="eyebrow">VERIFIED CAMPUS RECORDS</p><h1>Achievements</h1><p>Only approved records appear here.</p><div className="publicGrid">{items.map(a=><article key={a.id}><span>{a.category||"Achievement"} • {a.department||"RKGITM"}</span><h3>{a.title}</h3><strong>{a.studentName}</strong><p>{a.description}</p></article>)}</div>{items.length===0&&<div className="empty">No verified achievements published yet.</div>}</main>}
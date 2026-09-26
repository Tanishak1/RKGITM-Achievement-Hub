"use client";
import {useEffect,useMemo,useState} from "react";
const API=process.env.NEXT_PUBLIC_API_URL;
type A={id:number;title:string;studentName:string;department?:string;category?:string;description?:string;proofUrl?:string};
export default function Faculty(){
 const [items,setItems]=useState<A[]>([]),[msg,setMsg]=useState(""),[query,setQuery]=useState("");
 async function load(){if(!API){setMsg("Preview mode — connect the deployed backend to load live submissions.");return}try{const r=await fetch(API+"/api/achievements/pending");if(!r.ok)throw new Error();setItems(await r.json())}catch{setMsg("Verification service is currently unavailable.")}}
 useEffect(()=>{void load()},[]);
 async function decide(id:number,value:"APPROVED"|"REJECTED"){if(!API)return;await fetch(API+`/api/achievements/${id}/status?value=${value}`,{method:"PATCH"});setItems(v=>v.filter(x=>x.id!==id))}
 const shown=useMemo(()=>items.filter(a=>(a.title+" "+a.studentName+" "+(a.department||"")+" "+(a.category||"")).toLowerCase().includes(query.toLowerCase())),[items,query]);
 return <main className="adminShell"><aside className="sideNav"><div className="brandMark">RA</div><div><strong>Achievement Hub</strong><small>Faculty Console</small></div><nav><a className="active" href="/faculty">Verification Queue</a><a href="/achievements">Published Records</a><a href="/">Public Website</a></nav><div className="sideFoot">RKGITM • Internal MVP</div></aside>
 <section className="adminMain"><header className="adminTop"><div><p className="eyebrow">FACULTY CONSOLE</p><h1>Verification Queue</h1><p>Review evidence before achievements become part of the public institutional record.</p></div><div className="adminBadge">Reviewer</div></header>
 <div className="statRow"><div><span>Pending</span><strong>{items.length}</strong></div><div><span>Visible now</span><strong>{shown.length}</strong></div><div><span>Workflow</span><strong className="statusLive">● Active</strong></div></div>
 <div className="toolbar"><input aria-label="Search submissions" value={query} onChange={e=>setQuery(e.target.value)} placeholder="Search student, title, department…"/><span>{shown.length} submission{shown.length===1?"":"s"}</span></div>
 {msg&&<div className="adminNotice">{msg}</div>}<div className="reviewList">{shown.map(a=><article className="reviewCard" key={a.id}><div className="reviewMeta"><span>{a.category||"Achievement"}</span><span>{a.department||"Department not provided"}</span></div><h2>{a.title}</h2><strong>{a.studentName}</strong><p>{a.description}</p>{a.proofUrl&&<a className="proof" href={a.proofUrl} target="_blank" rel="noreferrer">View supporting evidence ↗</a>}<div className="reviewActions"><button onClick={()=>decide(a.id,"APPROVED")}>Approve record</button><button className="reject" onClick={()=>decide(a.id,"REJECTED")}>Reject</button></div></article>)}</div>
 {shown.length===0&&!msg&&<div className="adminEmpty"><span>✓</span><h2>Queue is clear.</h2><p>No submissions currently match this view.</p></div>}</section></main>
}
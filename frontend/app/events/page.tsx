"use client";
import {useEffect,useState} from "react";
const API=process.env.NEXT_PUBLIC_API_URL;
type Item={id:number;title:string;description?:string;category?:string;department?:string;linkUrl?:string};
export default function Page(){const [items,setItems]=useState<Item[]>([]),[loading,setLoading]=useState(true);
useEffect(()=>{if(!API){setLoading(false);return}fetch(API+"/api/content/public/EVENT").then(r=>r.ok?r.json():[]).then(setItems).catch(()=>setItems([])).finally(()=>setLoading(false))},[]);
return <main className="directoryPage"><a className="backLink" href="/">← Achievement Hub</a><header className="directoryHero"><p className="eyebrow">CAMPUS LIFE</p><h1>Events that shape<br/><em>the community.</em></h1><p>A verified showcase for societies, activities and events across RKGITM.</p></header>
{loading?<div className="contentState">Loading verified records…</div>:items.length?<section className="directoryGrid">{items.map(x=><article key={x.id}><span>{x.category||"EVENT"}</span><h2>{x.title}</h2><p>{x.description}</p><small>{x.department||"RKGITM"}</small>{x.linkUrl&&<a className="recordLink" href={x.linkUrl} target="_blank" rel="noreferrer">View details ↗</a>}</article>)}</section>:<div className="contentState"><strong>No published records yet.</strong><p>Verified content published by the administration will appear here automatically.</p></div>}</main>}
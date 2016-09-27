package fp.twitterscalaschool.examples

object scalaschoolExamples {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val chars = 'a' to 'z'                          //> chars  : scala.collection.immutable.NumericRange.Inclusive[Char] = NumericRa
                                                  //| nge(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, 
                                                  //| y, z)
  val perms = for {
	  a <- chars
	  b <- chars
	  if a != b
	} yield "%c%c".format(a, b)               //> perms  : scala.collection.immutable.IndexedSeq[String] = Vector(ab, ac, ad, 
                                                  //| ae, af, ag, ah, ai, aj, ak, al, am, an, ao, ap, aq, ar, as, at, au, av, aw, 
                                                  //| ax, ay, az, ba, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, 
                                                  //| br, bs, bt, bu, bv, bw, bx, by, bz, ca, cb, cd, ce, cf, cg, ch, ci, cj, ck, 
                                                  //| cl, cm, cn, co, cp, cq, cr, cs, ct, cu, cv, cw, cx, cy, cz, da, db, dc, de, 
                                                  //| df, dg, dh, di, dj, dk, dl, dm, dn, do, dp, dq, dr, ds, dt, du, dv, dw, dx, 
                                                  //| dy, dz, ea, eb, ec, ed, ef, eg, eh, ei, ej, ek, el, em, en, eo, ep, eq, er, 
                                                  //| es, et, eu, ev, ew, ex, ey, ez, fa, fb, fc, fd, fe, fg, fh, fi, fj, fk, fl, 
                                                  //| fm, fn, fo, fp, fq, fr, fs, ft, fu, fv, fw, fx, fy, fz, ga, gb, gc, gd, ge, 
                                                  //| gf, gh, gi, gj, gk, gl, gm, gn, go, gp, gq, gr, gs, gt, gu, gv, gw, gx, gy, 
                                                  //| gz, ha, hb, hc, hd, he, hf, hg, hi, hj, hk, hl, hm, hn, ho, hp, hq, hr, hs, 
                                                  //| ht, hu, hv, hw, hx, hy, hz, ia, ib, ic, id, ie, if, ig, ih, ij, ik, il, im, 
                                                  //| in, io, ip, iq, ir, is, 
                                                  //| Output exceeds cutoff limit.
	
	perms                                     //> res0: scala.collection.immutable.IndexedSeq[String] = Vector(ab, ac, ad, ae,
                                                  //|  af, ag, ah, ai, aj, ak, al, am, an, ao, ap, aq, ar, as, at, au, av, aw, ax,
                                                  //|  ay, az, ba, bc, bd, be, bf, bg, bh, bi, bj, bk, bl, bm, bn, bo, bp, bq, br,
                                                  //|  bs, bt, bu, bv, bw, bx, by, bz, ca, cb, cd, ce, cf, cg, ch, ci, cj, ck, cl,
                                                  //|  cm, cn, co, cp, cq, cr, cs, ct, cu, cv, cw, cx, cy, cz, da, db, dc, de, df,
                                                  //|  dg, dh, di, dj, dk, dl, dm, dn, do, dp, dq, dr, ds, dt, du, dv, dw, dx, dy,
                                                  //|  dz, ea, eb, ec, ed, ef, eg, eh, ei, ej, ek, el, em, en, eo, ep, eq, er, es,
                                                  //|  et, eu, ev, ew, ex, ey, ez, fa, fb, fc, fd, fe, fg, fh, fi, fj, fk, fl, fm,
                                                  //|  fn, fo, fp, fq, fr, fs, ft, fu, fv, fw, fx, fy, fz, ga, gb, gc, gd, ge, gf,
                                                  //|  gh, gi, gj, gk, gl, gm, gn, go, gp, gq, gr, gs, gt, gu, gv, gw, gx, gy, gz,
                                                  //|  ha, hb, hc, hd, he, hf, hg, hi, hj, hk, hl, hm, hn, ho, hp, hq, hr, hs, ht,
                                                  //|  hu, hv, hw, hx, hy, hz, ia, ib, ic, id, ie, if, ig, ih, ij, ik, il, im, in,
                                                  //|  io, ip, iq, ir, is, it,
                                                  //| Output exceeds cutoff limit.
}
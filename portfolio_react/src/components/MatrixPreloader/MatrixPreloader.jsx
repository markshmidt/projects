import React, { useEffect } from 'react';
import './MatrixPreloader.css';

const MatrixPreloader = () => {
  useEffect(() => {
    const canvas = document.getElementById('matrixCanvas');
    const main = document.getElementById('mainContent');


    if (!canvas || !main) return;
    const ctx = canvas.getContext('2d');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    const letters = '01';
    const fontSize = 20;
    const columns = Math.floor(canvas.width / fontSize);

    const drops = Array.from({ length: columns }, () => ({
      y: 1,
      speed: Math.random() * 5 + 5,
    }));

    const colors = [
      '#00b7ff', '#0099ff', '#3366ff',
      '#6633ff', '#9933ff', '#cc33ff', '#aa00ff'
    ];

    let fadeOut = false;
    let canvasOpacity = 1;

    const drawMatrix = () => {
      ctx.fillStyle = `rgba(0, 0, 0, ${fadeOut ? 0.00 : 0.1})`;
      ctx.fillRect(0, 0, canvas.width, canvas.height);
      ctx.font = `${fontSize}px monospace`;

      drops.forEach((drop, i) => {
        const text = letters[Math.floor(Math.random() * letters.length)];
        const color = colors[Math.floor(Math.random() * colors.length)];

        ctx.fillStyle = color;
        ctx.shadowBlur = 10;
        ctx.shadowColor = color;
        ctx.fillText(text, i * fontSize, drop.y * fontSize);

        ctx.shadowBlur = 0;
        ctx.shadowColor = 'transparent';

        drop.y += drop.speed;

        if (drop.y * fontSize > canvas.height && Math.random() > 0.975) {
          drop.y = 0;
          drop.speed = Math.random() * 3 + 2;
        }
      });

      if (fadeOut) {
        canvasOpacity -= 0.02;
        canvas.style.opacity = canvasOpacity;
        if (canvasOpacity <= 0) {
          clearInterval(matrixInterval);
          if (preloader) preloader.remove();
        }
      }
    };

    const matrixInterval = setInterval(drawMatrix, 20);
    setTimeout(() => {
      fadeOut = true;
      main.style.opacity = '1';
    }, 500);
  }, []);

  return (
    <div id="preloader">
      <canvas id="matrixCanvas"></canvas>
    </div>
  );
};

export default MatrixPreloader;
